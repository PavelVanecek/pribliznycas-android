package cz.corkscreewe.pribliznycas.app.helpers;

import android.content.res.Resources;
import android.util.Log;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by vanecekp on 19/09/14.
 */
public class MyTranslator {

    private static enum OPTS {
        ONE, FEW, OTHER
    }

    private static OPTS decide(int hours) {
        int hoursMod = hours % 12;
        if (hoursMod == 1 || hours == 0) {
            return OPTS.ONE;
        } else if (1 < hoursMod && hoursMod < 5) {
            return OPTS.FEW;
        } else {
            return OPTS.OTHER;
        }
    }

    /**
     * Because I want to force Czech grammar rules in default locale.
     * Blame me
     * @param hours
     * @return
     */
    public static String getHoursSoon(Resources res, int hours) {
        OPTS decision = decide(hours);
        int resourceId;
        switch (decision) {
            case ONE:
                resourceId = R.string.soon_hour_one;
                break;
            case FEW:
                resourceId = R.string.soon_hour_few;
                break;
            case OTHER:
                resourceId = R.string.soon_hour_other;
                break;
            default:
                resourceId = R.string.soon_hour_other;
        }
        return res.getString(resourceId);
    }

    /**
     *
     * @param res
     * @param hours
     * @return
     */
    public static String getHoursItWas(Resources res, int hours) {
        OPTS decision = decide(hours);
        int resourceId;
        switch (decision) {
            case ONE:
                resourceId = R.string.it_was_one;
                break;
            case FEW:
                resourceId = R.string.it_was_few;
                break;
            case OTHER:
                resourceId = R.string.it_was_other;
                break;
            default:
                resourceId = R.string.it_was_other;
        }
        return res.getString(resourceId);
    }

}
