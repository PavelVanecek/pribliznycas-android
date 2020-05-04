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
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Arrays;
import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

import static android.app.PendingIntent.FLAG_IMMUTABLE;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public abstract class TimeWidget extends AppWidgetProvider {

    private static final String ALARM_TICK = "cz.corkscreewe.pribliznycas.ALARM_TICK";
    private final int INTERVAL_MILLIS = 1000 * 60;

    protected abstract String setText(RemoteViews remoteViews, Context context, int activeTier);

    protected abstract int getHomescreenLayoutId();

    protected abstract int getKeyguardLayoutId();

    protected abstract int getDefaultTier();

    protected abstract int getMaxTier();

    private static final String ACTION_TIER_CHANGE_CLICK_UP = "cz.corkscreewe.pribliznycas.TIER_CHANGE_CLICK_ACTION_UP";
    private static final String ACTION_TIER_CHANGE_CLICK_DOWN = "cz.corkscreewe.pribliznycas.TIER_CHANGE_CLICK_ACTION_DOWN";
    private static final String ACTIVE_TIERS_PREFERENCE = "cz.corkscreewe.pribliznycas.ACTIVE_TIERS_PREFERENCE";
    private final int ALARM_INTENT_ID = 1;

    @Override
    public void onUpdate(@NonNull Context context, AppWidgetManager appWidgetManager, @NonNull int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            int activeTier = getActiveTier(context, appWidgetId);
            updateWidget(context, AppWidgetManager.getInstance(context), appWidgetId, activeTier);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void registerAlarmIntent(@NonNull Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        d("registering alarm");
        PendingIntent pendingIntent = getPendingIntent(context);
        Calendar calendar = Calendar.getInstance();
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis() + INTERVAL_MILLIS, INTERVAL_MILLIS, pendingIntent);
    }

    private PendingIntent getPendingIntent(@NonNull Context context) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(ALARM_TICK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PendingIntent.getBroadcast(context, ALARM_INTENT_ID, intent, FLAG_IMMUTABLE);
        } else {
            return PendingIntent.getBroadcast(context, ALARM_INTENT_ID, intent, FLAG_UPDATE_CURRENT);
        }
    }

    private void cancelAlarmIntent(@NonNull Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent(context));
    }

    private void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter();
        // For some reason, ACTION_TIME_TICK is not received reliably - so Alarm it is.
//        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            d("using application context");
            applicationContext.registerReceiver(this, intentFilter);
        } else {
            Log.wtf("applicationContext", "is null and should not be null");
        }
        d("registering receiver");
    }

    private void unregisterReceiver(@NonNull Context context) {
        try {
            context.getApplicationContext().unregisterReceiver(this);
        } catch (IllegalArgumentException e) {
            // It may happen that the receiver is already unregistered by system; in that case do nothing.
        }
    }

    @NonNull
    private RemoteViews setupWidget(@NonNull Context applicationContext, @NonNull AppWidgetManager appWidgetManager, int appWidgetId) {
        boolean isKeyguard = isKeyguard(appWidgetManager, appWidgetId);
        if (isKeyguard) {
            return setupKeyguardWidgetIntents(applicationContext, appWidgetId);
        } else {
            return setupHomescreenWidgetIntents(applicationContext, appWidgetId);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressWarnings("InlinedApi, NewApi")
    private boolean isKeyguard(@NonNull AppWidgetManager appWidgetManager, int appWidgetId) {
        boolean isKeyguard = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) { // 17
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            int category = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY);
            return category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
        }
        return false;
    }

    @NonNull
    private RemoteViews setupWidgetBase(@NonNull Context applicationContext, int appWidgetId, int templateId) {
        d("setupWidgetBase", appWidgetId);

        RemoteViews remoteViews = new RemoteViews(applicationContext.getPackageName(), templateId);

        Intent nextIntent = new Intent(applicationContext, getClass());
        nextIntent.setAction(ACTION_TIER_CHANGE_CLICK_UP);
        nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent nextPendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nextPendingIntent = PendingIntent.getBroadcast(applicationContext, appWidgetId, nextIntent, FLAG_IMMUTABLE);
        } else {
            nextPendingIntent = PendingIntent.getBroadcast(applicationContext, appWidgetId, nextIntent, FLAG_UPDATE_CURRENT);
        }

        Intent prevIntent = new Intent(applicationContext, getClass());
        prevIntent.setAction(ACTION_TIER_CHANGE_CLICK_DOWN);
        prevIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent prevPendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            prevPendingIntent = PendingIntent.getBroadcast(applicationContext, appWidgetId, prevIntent, FLAG_IMMUTABLE);
        } else {
            prevPendingIntent = PendingIntent.getBroadcast(applicationContext, appWidgetId, prevIntent, FLAG_UPDATE_CURRENT);
        }

        remoteViews.setOnClickPendingIntent(R.id.button_next, nextPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.button_prev, prevPendingIntent);

        return remoteViews;
    }

    @NonNull
    private RemoteViews setupKeyguardWidgetIntents(@NonNull Context applicationContext, int appWidgetId) {
        int layoutId = getKeyguardLayoutId();
        return setupWidgetBase(applicationContext, appWidgetId, layoutId);
    }

    @NonNull
    private RemoteViews setupHomescreenWidgetIntents(@NonNull Context applicationContext, int appWidgetId) {
        int layoutId = getHomescreenLayoutId();
        return setupWidgetBase(applicationContext, appWidgetId, layoutId);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        d("onEnabled");
        registerReceiver(context);
        registerAlarmIntent(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        d("onDeleted", appWidgetIds);
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVE_TIERS_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < appWidgetIds.length; i++) {
            editor.remove(String.valueOf(i));
        }
        editor.apply();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AppWidgetManager manager = AppWidgetManager.getInstance(context.getApplicationContext());
        int[] appWidgetIds = getAppWidgetIds(context.getApplicationContext(), manager);

        d("onDisabled", appWidgetIds);

        // last widget has been removed, cancel the alarmManager
        if (appWidgetIds.length == 0) {
            unregisterReceiver(context);
            cancelAlarmIntent(context);
            cleanupPreferences(context);
        }
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        String action = intent.getAction();
        d("intent received: " + action);
        if (action == null) {
            return;
        }
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
                    d("ACTION_APPWIDGET_UPDATE", intentWidgetId);
                    if (intentWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                        updateAllWidgets(context, manager);
                    } else {
                        updateWidget(context, manager, intentWidgetId, getActiveTier(context, intentWidgetId));
                    }
                }
                break;
            }
            case ALARM_TICK:
                // case Intent.ACTION_TIME_TICK:
            case Intent.ACTION_TIME_CHANGED:
            case Intent.ACTION_TIMEZONE_CHANGED: {
                updateAllWidgets(context, manager);
                break;
            }
        }

        // Now. Android sometimes forgets Alarms for reasons. Some state is left where it should not,
        // and my alarms are lost. It is safe to just call register again all the time,
        // because the system will just reuse the same PendingIntent if one is already registered
        // (thanks to the FLAG_IMMUTABLE) and so we're done here.
        registerAlarmIntent(context);
        super.onReceive(context, intent);
    }

    private void updateAllWidgets(@NonNull Context context, AppWidgetManager manager) {
        int[] appWidgetIds = getAppWidgetIds(context, manager);
        d("updating all widgets ", appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
            int activeTier = getActiveTier(context, appWidgetId);
            updateWidget(context, manager, appWidgetId, activeTier);
        }
    }

    private void updateWidget(@NonNull Context context, @NonNull AppWidgetManager manager, int appWidgetId, int activeTier) {
        RemoteViews remoteViews = setupWidget(context.getApplicationContext(), manager, appWidgetId);
        String time = setText(remoteViews, context, activeTier);
        setContentDescription(context, remoteViews, time);
        manager.updateAppWidget(appWidgetId, remoteViews);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setContentDescription(@NonNull Context context, @NonNull RemoteViews remoteViews, String time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) { // 15
            remoteViews.setContentDescription(R.id.button_prev, time + context.getString(R.string.btn_desc_less_precise));
            remoteViews.setContentDescription(R.id.button_next, time + context.getString(R.string.btn_desc_more_precise));
        }
    }

    private int[] getAppWidgetIds(@NonNull Context context, @NonNull AppWidgetManager manager) {
        ComponentName widget = new ComponentName(context, this.getClass());
        return manager.getAppWidgetIds(widget);
    }

    private int getActiveTier(@NonNull Context context, int appWidgetId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVE_TIERS_PREFERENCE, MODE_PRIVATE);
        int min = 0;
        int max = getMaxTier();
        int tier = Math.min(max, Math.max(min, sharedPreferences.getInt(String.valueOf(appWidgetId), getDefaultTier())));
        return tier;
    }

    private void updateTier(@NonNull Context context, int appWidgetId, int delta, @NonNull AppWidgetManager manager) {
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

    private void cleanupPreferences(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(ACTIVE_TIERS_PREFERENCE, MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    private void d(String msg) {
        Log.d(getClass().getName(), msg);
    }

    private void d(String msg, int i) {
        d(msg + ": " + i);
    }

    private void d(String msg, int[] arr) {
        d(msg + ": " + Arrays.toString(arr));
    }

}
