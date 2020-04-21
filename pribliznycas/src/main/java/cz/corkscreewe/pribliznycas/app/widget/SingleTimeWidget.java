package cz.corkscreewe.pribliznycas.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.ITier;
import cz.corkscreewe.pribliznycas.app.tier.Tier0;
import cz.corkscreewe.pribliznycas.app.tier.Tier1;
import cz.corkscreewe.pribliznycas.app.tier.Tier2;
import cz.corkscreewe.pribliznycas.app.tier.Tier25;
import cz.corkscreewe.pribliznycas.app.tier.Tier3;
import cz.corkscreewe.pribliznycas.app.tier.Tier4;
import cz.corkscreewe.pribliznycas.app.tier.Tier5;
import cz.corkscreewe.pribliznycas.app.tier.Tier6;

/**
 * Created by cork on 29.05.14.
 */
public class SingleTimeWidget extends TimeWidget {

    private final ITier[] tiers = {
            new Tier0(),
            new Tier1(),
            new Tier2(),
            new Tier3(),
            new Tier25(),
            new Tier4(),
            new Tier5(),
            new Tier6()
    };

    @Override
    protected String setText(RemoteViews remoteViews, Context context, int activeTier) {
        Calendar c = new GregorianCalendar();
        Resources res = context.getResources();
        String time = tiers[activeTier].getApproxTime(c, res);
        if (time != null) {
            Log.i("single widget", time);
            remoteViews.setTextViewText(R.id.appwidget_text, time);
        }
        return time;
    }

    @Override
    protected int getHomescreenLayoutId() {
        return R.layout.single_time_widget;
    }

    @Override
    protected int getKeyguardLayoutId() {
        return R.layout.time_widget_keyguard;
    }

    @Override
    protected int getDefaultTier() {
        return 5;
    }

    @Override
    protected int getMaxTier() {
        return tiers.length - 1;
    }
}
