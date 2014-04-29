package cz.corkscreewe.pribliznycas.app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class TimeWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, TimeService.class));
        Log.i("widget", "updating");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = setupWidget(context, appWidgetManager, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            Intent installedIntent = new Intent(TimeService.ACTION_UPDATE_WIDGET);
            installedIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            context.sendBroadcast(installedIntent);
            Log.d("widget", "sending created intent with id " + appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews setupWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        boolean isKeyguard = false;

        // it might happen that the widget is running on keyguard screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int category = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY);
            // keyguard widget doesn't show buttons
            if (category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD) {
                Log.d("widget", "is keyguard!");
                isKeyguard = true;
            }
        }
        if (isKeyguard) {
            return setupKeyguardWidgetIntents(context, appWidgetId);
        } else {
            return setupHomescreenWidgetIntents(context, appWidgetId);
        }
    }

    private RemoteViews setupWidgetBase(Context context, int appWidgetId, int templateId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), templateId);

        Intent nextIntent = new Intent(TimeService.ACTION_UPDATE_WIDGET_UP);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, nextIntent, 0);

        Intent prevIntent = new Intent(TimeService.ACTION_UPDATE_WIDGET_DOWN);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, appWidgetId + 1000, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_prev, prevPendingIntent);
        return remoteViews;
    }

    private RemoteViews setupKeyguardWidgetIntents(Context context, int appWidgetId) {
        return setupWidgetBase(context, appWidgetId, R.layout.time_widget_keyguard);
    }

    private RemoteViews setupHomescreenWidgetIntents(Context context, int appWidgetId) {
        return setupWidgetBase(context, appWidgetId, R.layout.time_widget_buttons);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("widget", "onEnabled");
        context.startService(new Intent(context, TimeService.class));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i("widget", "onDisabled");
        context.stopService(new Intent(context, TimeService.class));
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("widget", "received intent " + intent.getAction());
        if (TimeService.ACTION_TIME_CHANGE.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String time = extras.getString("time");
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                ComponentName widget = new ComponentName(context, this.getClass());
                int[] appWidgetIds = manager.getAppWidgetIds(widget);

                for (int appWidgetId : appWidgetIds) {
                    RemoteViews remoteViews = setupWidget(context, manager, appWidgetId);
                    Log.d("widget", "setting text on widget id " + appWidgetId + " to " + time);
                    remoteViews.setTextViewText(R.id.appwidget_text, time);
                    manager.updateAppWidget(widget, remoteViews);
                }
            }
        }
        super.onReceive(context, intent);
    }

}


