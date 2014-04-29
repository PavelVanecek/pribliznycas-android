package cz.corkscreewe.pribliznycas.app;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.tiers.Tier0;
import cz.corkscreewe.pribliznycas.app.tiers.Tier1;
import cz.corkscreewe.pribliznycas.app.tiers.Tier2;
import cz.corkscreewe.pribliznycas.app.tiers.Tier3;
import cz.corkscreewe.pribliznycas.app.tiers.Tier4;
import cz.corkscreewe.pribliznycas.app.tiers.Tier5;
import cz.corkscreewe.pribliznycas.app.tiers.Tier6;

/**
 * Created by cork on 25.04.14.
 */
public class TimeService extends Service {

    // widget update action
    public static final String ACTION_UPDATE_WIDGET = "cz.corkscreewe.pribliznycas.UPDATE_WIDGET_ACTION";
    public static final String ACTION_UPDATE_WIDGET_UP = "cz.corkscreewe.pribliznycas.UPDATE_WIDGET_ACTION_UP";
    public static final String ACTION_UPDATE_WIDGET_DOWN = "cz.corkscreewe.pribliznycas.UPDATE_WIDGET_ACTION_DOWN";

    // a command to announce time has changed
    public static final String ACTION_TIME_CHANGE = "cz.corkscreewe.pribliznycas.TIME_CHANGE_ACTION";

    private ITier[] tiers = {
            new Tier0(),
            new Tier1(),
            new Tier2(),
            new Tier3(),
            new Tier4(),
            new Tier5(),
            new Tier6()
    };

    private int activeTier = 5;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("service", "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("service", "onDestroy");
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void onCreate() {
        Log.i("service", "starting");

        IntentFilter intentFilter = new IntentFilter();
        // You can not receive this through components declared in manifests,
        // only by explicitly registering for it with Context.registerReceiver()
        // http://developer.android.com/reference/android/content/Intent.html#ACTION_TIME_TICK
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
//        intentFilter.addAction(MainActivity.SEEKBAR_PROGRESS_CHANGE);
        intentFilter.addAction(TimeService.ACTION_UPDATE_WIDGET);
        intentFilter.addAction(TimeService.ACTION_UPDATE_WIDGET_UP);
        intentFilter.addAction(TimeService.ACTION_UPDATE_WIDGET_DOWN);

        registerReceiver(receiver, intentFilter);

        sendRefresh();
    }

    public void setTier(int tier) {
        if (tier >= 0 && tier < tiers.length) {
            this.activeTier = tier;
            sendRefresh();
        }
    }

    protected void sendRefresh() {
        String time = getApproxTime();
        Intent intent = new Intent(ACTION_TIME_CHANGE);
        intent.putExtra("tier", activeTier);
        intent.putExtra("time", time);
        sendBroadcast(intent);
        Log.d("service", "sending refresh broadcast: " + time);

    }

    private String getApproxTime() {
        Calendar c = new GregorianCalendar();
        Resources res = getApplicationContext().getResources();
        return tiers[activeTier].getApproxTime(c, res);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int tier = intent.getIntExtra("tier", getActiveTier());
            if (ACTION_UPDATE_WIDGET_UP.equals(intent.getAction())) {
                setTier(tier + 1);
            } else if (ACTION_UPDATE_WIDGET_DOWN.equals(intent.getAction())) {
                setTier(tier - 1);
            } else {
                setTier(tier);
            }
            Log.d("receiver", "received action " + intent.getAction());
            sendRefresh();
        }
    };

    public int getActiveTier() {
        return activeTier;
    }
}
