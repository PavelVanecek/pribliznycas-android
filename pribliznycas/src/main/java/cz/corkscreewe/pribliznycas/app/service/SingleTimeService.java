package cz.corkscreewe.pribliznycas.app.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import java.util.Calendar;
import java.util.GregorianCalendar;
import cz.corkscreewe.pribliznycas.app.tier.ITier;
import cz.corkscreewe.pribliznycas.app.tier.Tier0;
import cz.corkscreewe.pribliznycas.app.tier.Tier1;
import cz.corkscreewe.pribliznycas.app.tier.Tier2;
import cz.corkscreewe.pribliznycas.app.tier.Tier3;
import cz.corkscreewe.pribliznycas.app.tier.Tier4;
import cz.corkscreewe.pribliznycas.app.tier.Tier5;
import cz.corkscreewe.pribliznycas.app.tier.Tier6;
import cz.corkscreewe.pribliznycas.app.widget.SingleTimeWidget;

/**
 * Created by cork on 29.05.14.
 */
public class SingleTimeService extends TimeService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("single time service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    private ITier[] tiers = {
            new Tier0(),
            new Tier1(),
            new Tier2(),
            new Tier3(),
            new Tier4(),
            new Tier5(),
            new Tier6()
    };

    @Override
    protected int getDefaultTier() {
        return 5;
    }

    @Override
    protected int getMaxTier() {
        return tiers.length - 1;
    }

    protected ComponentName getComponentName() {
        return new ComponentName(this, SingleTimeWidget.class);
    }

    @Override
    protected void sendRefresh(int appWidgetId) {
        Log.i("single time service", "sendRefresh");
        super.sendRefresh(appWidgetId);
    }

    @Override
    protected void augmentIntent(Intent intent, int activeTier) {
        Log.d("single time augment", "trying to fetch string for tier " + activeTier);
        Calendar c = new GregorianCalendar();
        Resources res = getApplicationContext().getResources();
        intent.putExtra("time", tiers[activeTier].getApproxTime(c, res));
    }
}
