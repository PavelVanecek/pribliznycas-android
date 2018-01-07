package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

/**
 * Created by cork on 29.05.14.
 */
public class DoubleTier implements IDoubleTier {

    private final ITier upTier;
    private final ITier downTier;

    public DoubleTier(ITier upTier, ITier downTier) {
        this.upTier = upTier;
        this.downTier = downTier;
    }

    @Override
    public String[] getDoubleApproxTime(Calendar c, Resources res) {
        return new String[]{
                upTier.getApproxTime(c, res),
                downTier.getApproxTime(c, res)
        };
    }
}
