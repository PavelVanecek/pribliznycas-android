package cz.corkscreewe.pribliznycas.app.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.tier.DoubleTier;
import cz.corkscreewe.pribliznycas.app.tier.IDoubleTier;
import cz.corkscreewe.pribliznycas.app.tier.Tier0;
import cz.corkscreewe.pribliznycas.app.tier.Tier1;
import cz.corkscreewe.pribliznycas.app.tier.Tier2;
import cz.corkscreewe.pribliznycas.app.tier.Tier25;
import cz.corkscreewe.pribliznycas.app.tier.Tier3;
import cz.corkscreewe.pribliznycas.app.tier.Tier4;
import cz.corkscreewe.pribliznycas.app.tier.Tier5;
import cz.corkscreewe.pribliznycas.app.tier.Tier7;
import cz.corkscreewe.pribliznycas.app.widget.DoubleTimeWidget;

/**
 * Created by cork on 29.05.14.
 */
public class DoubleTimeService extends TimeService {

    public static final String DOUBLE_TIME_EXTRA = "doubletime";

    private final IDoubleTier[] tiers = {
            new DoubleTier(new Tier0(), new Tier1()),
            new DoubleTier(new Tier2(), new Tier1()),
            new DoubleTier(new Tier2(), new Tier25()),
            new DoubleTier(new Tier25(), new Tier3()),
            new DoubleTier(new Tier2(), new Tier3()),
            new DoubleTier(new Tier3(), new Tier4()),
            new DoubleTier(new Tier5(), new Tier4()),
            new Tier7()
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("double time service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected int getDefaultTier() {
        return 5;
    }

    @Override
    protected int getMaxTier() {
        return tiers.length - 1;
    }

    @Override
    protected void augmentIntent(Intent intent, int activeTier) {
        Log.d("double time augment", "trying to fetch string for tier " + activeTier);
        Calendar c = new GregorianCalendar();
        Resources res = getApplicationContext().getResources();
        intent.putExtra(DOUBLE_TIME_EXTRA, tiers[activeTier].getDoubleApproxTime(c, res));
    }

    @Override
    protected void sendRefresh(int appWidgetId) {
        super.sendRefresh(appWidgetId);
    }

    @Override
    protected ComponentName getComponentName() {
        return new ComponentName(this, DoubleTimeWidget.class);
    }
}
