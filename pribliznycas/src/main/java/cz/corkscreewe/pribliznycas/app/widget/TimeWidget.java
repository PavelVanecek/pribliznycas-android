package cz.corkscreewe.pribliznycas.app.widget;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.service.TimeService;

/**
 * Implementation of App Widget functionality.
 */
public abstract class TimeWidget extends AppWidgetProvider {

    protected abstract String setText(RemoteViews remoteViews, Bundle intentExtras);
    protected abstract int getHomescreenLayoutId();
    protected abstract int getKeyguardLayoutId();
    protected abstract Intent getServiceIntent(Context context, int appWidgetId);

    private List<PendingIntent> intents = new ArrayList<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        startServices(context, appWidgetIds);

        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.registerReceiver(this, intentFilter);
        } else {
            Log.wtf("applicationContext", "is null and should not be null");
        }
        Log.d("widget", "registering receiver");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getAlarmPendingIntent(context);
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000 * 60, pendingIntent);
        Log.d("widget", "registering alarm");

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private PendingIntent getAlarmPendingIntent(Context context) {
        Intent intent = getServiceIntent(context, AppWidgetManager.INVALID_APPWIDGET_ID);
        return PendingIntent.getService(context, 0, intent, 0);
    }

    private void startServices(Context context, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = getServiceIntent(context, appWidgetId);
            Log.d("widget", "starting service");
            context.startService(intent);
        }
    }

    private RemoteViews setupWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // it might happen that the widget is running on keyguard screen
        boolean isKeyguard = isKeyguard(appWidgetManager, appWidgetId);
        if (isKeyguard) {
            return setupKeyguardWidgetIntents(context, appWidgetId);
        } else {
            return setupHomescreenWidgetIntents(context, appWidgetId);
        }
    }

    /**
     * @param appWidgetManager
     * @param appWidgetId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("InlinedApi, NewApi")
    private boolean isKeyguard(AppWidgetManager appWidgetManager, int appWidgetId) {
        boolean isKeyguard = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // 17
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int category = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY);
            if (category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD) {
                isKeyguard = true;
            }
        }
        Log.d("isKeyguard", String.valueOf(isKeyguard));
        return isKeyguard;
    }

    private RemoteViews setupWidgetBase(Context context, int appWidgetId, int templateId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), templateId);

        Intent nextIntent = getServiceIntent(context, appWidgetId);
        nextIntent.setAction("up " + appWidgetId);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        nextIntent.putExtra("change", +1);
        PendingIntent nextPendingIntent = PendingIntent.getService(context, appWidgetId, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent prevIntent = getServiceIntent(context, appWidgetId);
        prevIntent.setAction("down " + appWidgetId);
        prevIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        prevIntent.putExtra("change", -1);
        PendingIntent prevPendingIntent = PendingIntent.getService(context, appWidgetId, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_prev, prevPendingIntent);

//        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        int color = defaultSharedPreferences.getInt("preference_font_color_button_key", Color.WHITE);
//        remoteViews.setTextColor(R.id.appwidget_text, color);

        return remoteViews;
    }

    private RemoteViews setupPreferenceButton(Context context, int appWidgetId, RemoteViews remoteViews) {
//        Intent preferenceIntent = new Intent(context, TimeWidgetConfiguration.class);
//        preferenceIntent.setAction("preference " + appWidgetId);
//        preferenceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        PendingIntent preferencePendingIntent = PendingIntent.getActivity(context, appWidgetId, preferenceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        remoteViews.setOnClickPendingIntent(R.id.settingsButton, preferencePendingIntent);
        return remoteViews;
    }

    private RemoteViews setupKeyguardWidgetIntents(Context context, int appWidgetId) {
        int layoutId = getKeyguardLayoutId();
        return setupWidgetBase(context, appWidgetId, layoutId);
    }

    private RemoteViews setupHomescreenWidgetIntents(Context context, int appWidgetId) {
        int layoutId = getHomescreenLayoutId();
        RemoteViews remoteViews = setupWidgetBase(context, appWidgetId, layoutId);
        return setupPreferenceButton(context, appWidgetId, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = getAppWidgetIds(context, manager);
        startServices(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = getAppWidgetIds(context, manager);

        // last widget has been removed, cancel the alarmManager
        if (appWidgetIds.length == 0) {
            PendingIntent timeServicePendingIntent = getAlarmPendingIntent(context);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(timeServicePendingIntent);
        }
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("widget", "intent received: " + action);

        // intent from service = redraw widgets
        if (TimeService.ACTION_TIME_CHANGE.equals(action)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = getAppWidgetIds(context, manager);

                int intentWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                for (int appWidgetId : appWidgetIds) {
                    if (
                        intentWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID ||
                        intentWidgetId == appWidgetId
                    ) {
                        RemoteViews remoteViews = setupWidget(context, manager, appWidgetId);
                        String time = setText(remoteViews, extras);
                        setContentDescription(context, remoteViews, time);
                        // this will read aloud every update every minute;
//                        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
//                        if (accessibilityManager.isEnabled()) {
//                            Log.d("a11y", "firing TYPE_ANNOUNCEMENT event, including source");
//                            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
//                            accessibilityEvent.setEventType(AccessibilityEvent.TYPE_ANNOUNCEMENT);
//                            accessibilityEvent.setPackageName(context.getPackageName());
//                            accessibilityEvent.setClassName(context.getClass().getName());
//                            accessibilityEvent.getText().add(time);
//                            accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
//                            Toast.makeText(context, time, Toast.LENGTH_SHORT).show();
//                        }
                        manager.updateAppWidget(appWidgetId, remoteViews);
                    }
                }
            }
        }

        if (
                Intent.ACTION_BOOT_COMPLETED.equals(action) ||
                Intent.ACTION_TIME_TICK.equals(action) ||
                Intent.ACTION_TIME_CHANGED.equals(action) ||
                Intent.ACTION_TIMEZONE_CHANGED.equals(action)
                ) {
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = getAppWidgetIds(context, manager);
            startServices(context, appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    /**
     *
     * @param context
     * @param remoteViews
     * @param time
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setContentDescription(Context context, RemoteViews remoteViews, String time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) { // 15
            remoteViews.setContentDescription(R.id.button_prev, time + context.getString(R.string.btn_desc_less_precise));
            remoteViews.setContentDescription(R.id.button_next, time + context.getString(R.string.btn_desc_more_precise));
        }
    }

    private int[] getAppWidgetIds(Context context, AppWidgetManager manager) {
        ComponentName widget = new ComponentName(context, this.getClass());
        return manager.getAppWidgetIds(widget);
    }

}


