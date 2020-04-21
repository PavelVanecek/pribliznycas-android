package cz.corkscreewe.pribliznycas.app.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import cz.corkscreewe.pribliznycas.app.R;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public abstract class TimeWidget extends AppWidgetProvider {

    protected abstract String setText(RemoteViews remoteViews, Context context, int activeTier);

    protected abstract int getHomescreenLayoutId();

    protected abstract int getKeyguardLayoutId();

    protected abstract int getDefaultTier();

    protected abstract int getMaxTier();

    private static final String ACTION_TIER_CHANGE_CLICK_UP = "cz.corkscreewe.pribliznycas.TIER_CHANGE_CLICK_ACTION_UP";
    private static final String ACTION_TIER_CHANGE_CLICK_DOWN = "cz.corkscreewe.pribliznycas.TIER_CHANGE_CLICK_ACTION_DOWN";
    private static final String ACTIVE_TIERS_PREFERENCE = "cz.corkscreewe.pribliznycas.ACTIVE_TIERS_PREFERENCE";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
//        intentFilter.addAction(ACTION_TIER_CHANGE_CLICK);
//        intentFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            applicationContext.registerReceiver(this, intentFilter);
        } else {
            Log.wtf("applicationContext", "is null and should not be null");
        }
        Log.d("widget", "registering receiver");

//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        PendingIntent pendingIntent = getAlarmPendingIntent(context);
//        alarmManager.cancel(pendingIntent);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 1000 * 60, pendingIntent);
//        Log.d("widget", "registering alarm");

        for (int appWidgetId : appWidgetIds) {
            int activeTier = getActiveTier(context, appWidgetId);
            updateWidget(context, AppWidgetManager.getInstance(context), appWidgetId, activeTier);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
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
        Log.d("widget", "setupWidgetBase");
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), templateId);

        Intent nextIntent = new Intent(context, getClass());
        nextIntent.setAction(ACTION_TIER_CHANGE_CLICK_UP);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent prevIntent = new Intent(context, getClass());
        prevIntent.setAction(ACTION_TIER_CHANGE_CLICK_DOWN);
        prevIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, appWidgetId, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        remoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_prev, prevPendingIntent);

        return remoteViews;
    }

    private RemoteViews setupKeyguardWidgetIntents(Context context, int appWidgetId) {
        int layoutId = getKeyguardLayoutId();
        return setupWidgetBase(context, appWidgetId, layoutId);
    }

    private RemoteViews setupHomescreenWidgetIntents(Context context, int appWidgetId) {
        int layoutId = getHomescreenLayoutId();
        return setupWidgetBase(context, appWidgetId, layoutId);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d("widget", "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        Log.d("widget", "onDisabled");
        super.onDisabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("widget", "intent received: " + action);
        if (action == null) { return; }
        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        switch (action) {
            case ACTION_TIER_CHANGE_CLICK_UP: {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int intentWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    updateTier(context, intentWidgetId, +1, manager);
                }
                break;
            }
            case ACTION_TIER_CHANGE_CLICK_DOWN: {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int intentWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    updateTier(context, intentWidgetId, -1, manager);
                }
                break;
            }
            case ACTION_APPWIDGET_UPDATE: {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int intentWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    d("ACTION_APPWIDGET_UPDATE: " + intentWidgetId);
                    updateWidget(context, manager, intentWidgetId, getActiveTier(context, intentWidgetId));
                }
                break;
            }
            default: {
                Bundle extras = intent.getExtras();
                Log.d("widget", "extras:" + extras);
                if (extras != null) {
                    int intentWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    updateWidget(context, manager, intentWidgetId, getActiveTier(context, intentWidgetId));
                } else {
                    int[] appWidgetIds = getAppWidgetIds(context, manager);

                    for (int appWidgetId : appWidgetIds) {
                        int activeTier = getActiveTier(context, appWidgetId);
                        updateWidget(context, manager, appWidgetId, activeTier);
                    }
                }
            }
        }
        super.onReceive(context, intent);
    }

    private void updateWidget(Context context, AppWidgetManager manager, int appWidgetId, int activeTier) {
        RemoteViews remoteViews = setupWidget(context, manager, appWidgetId);
        String time = setText(remoteViews, context, activeTier);
        setContentDescription(context, remoteViews, time);
        manager.updateAppWidget(appWidgetId, remoteViews);
    }

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

    protected int getActiveTier(Context context, int appWidgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVE_TIERS_PREFERENCE, MODE_PRIVATE);
        int min = 0;
        int max = getMaxTier();
        int tier = Math.min(max, Math.max(min, sharedPreferences.getInt(String.valueOf(appWidgetId), getDefaultTier())));
        d("active tier: " + tier);
        return tier;
    }

    private void updateTier(Context context, int appWidgetId, int delta, AppWidgetManager manager) {
        d("updateTier: " + delta);
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVE_TIERS_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int maxTier = getMaxTier();
        int newActiveTier = getActiveTier(context, appWidgetId) + delta;
        if (newActiveTier >= 0 && newActiveTier <= maxTier) {
            editor.putInt(String.valueOf(appWidgetId), newActiveTier);
            editor.apply();
            updateWidget(context, manager, appWidgetId, newActiveTier);
        }
    }

    private void d(String msg) {
        Log.d(getClass().getName(), msg);
    }

}
