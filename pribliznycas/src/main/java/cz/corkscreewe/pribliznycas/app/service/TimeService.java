package cz.corkscreewe.pribliznycas.app.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by cork on 25.04.14.
 */
public abstract class TimeService extends Service {

    protected abstract int getDefaultTier();
    protected abstract int getMaxTier();

    protected abstract ComponentName getComponentName();
    protected abstract void augmentIntent(Intent intent, int activeTier);

    private static final String ACTIVE_TIERS_PREFERENCE = "cz.corkscreewe.pribliznycas.ACTIVE_TIERS_PREFERENCE";

    // a command to announce time has changed
    public static final String ACTION_TIME_CHANGE = "cz.corkscreewe.pribliznycas.TIME_CHANGE_ACTION";

    // tier which will be selected if no previous preference is found
    private final int DEFAULT_TIER = getDefaultTier();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // intent might be sometimes null. just because
        Log.d("service", "onStartCommand");
        if (intent == null) {
            sendRefresh(AppWidgetManager.INVALID_APPWIDGET_ID);
        } else {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
                ComponentName componentName = getComponentName();
                int[] appWidgetIds = widgetManager.getAppWidgetIds(componentName);
                Log.i("time service widgets:", Arrays.toString(appWidgetIds));
                Log.i("time service class", this.getClass().getName());
                for (int fetchedAppWidgetId : appWidgetIds) {
                    sendRefresh(fetchedAppWidgetId);
                }
            } else {
                int tier = intent.getIntExtra("tier", getActiveTier(appWidgetId));
                int change = intent.getIntExtra("change", 0);
                setTier(tier + change, appWidgetId);
                sendRefresh(appWidgetId);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Refreshes one single widget
     * @param appWidgetId widget ID or AppWidgetManager.INVALID_APPWIDGET_ID
     */
    void sendRefresh(int appWidgetId) {
        int activeTier = getActiveTier(appWidgetId);
        Intent intent = new Intent(ACTION_TIME_CHANGE);
        intent.putExtra("tier", getDefaultTier());
        augmentIntent(intent, activeTier);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        sendBroadcast(intent);
        Log.d("service", "sending refresh broadcast to widget id " + appWidgetId);
    }

    private int getActiveTier(int appWidgetId) {
        SharedPreferences sharedPreferences = getSharedPreferences(ACTIVE_TIERS_PREFERENCE, 0);
        return sharedPreferences.getInt(String.valueOf(appWidgetId), DEFAULT_TIER);
    }

    private void setTier(int tier, int appWidgetId) {
        int maxTier = getMaxTier();
        if (tier >= 0 && tier <= maxTier) {
            Log.d("tier", "setting widget " + appWidgetId + " to tier " + tier);
            SharedPreferences sharedPreferences = getSharedPreferences(ACTIVE_TIERS_PREFERENCE, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(String.valueOf(appWidgetId), tier);
            editor.commit();
        }
    }

}
