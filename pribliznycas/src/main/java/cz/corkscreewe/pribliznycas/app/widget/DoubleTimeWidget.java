package cz.corkscreewe.pribliznycas.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.R;
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

/**
 * Created by cork on 29.05.14.
 */
public class DoubleTimeWidget extends TimeWidget {

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

    @Nullable
    @Override
    protected String setText(@NonNull RemoteViews remoteViews, @NonNull Context context, int activeTier) {
        Calendar c = new GregorianCalendar();
        Resources res = context.getResources();
        String[] times = tiers[activeTier].getDoubleApproxTime(c, res);
        if (times != null && times.length == 2) {
            Log.i("double widget", times[0] + ", " + times[1]);
            remoteViews.setTextViewText(R.id.double_appwidget_text_up, times[0]);
            remoteViews.setTextViewText(R.id.double_appwidget_text_bottom, times[1]);
            return times[0] + " " + times[1];
        } else {
            return null;
        }
    }

    @Override
    protected int getHomescreenLayoutId() {
        return R.layout.double_time_widget_buttons;
    }

    @Override
    protected int getKeyguardLayoutId() {
        return R.layout.double_time_widget_keyguard;
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
