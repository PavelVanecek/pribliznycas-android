package cz.corkscreewe.pribliznycas.app.helpers;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by vanecekp on 19/09/14.
 */
public class MyTranslator {

    private enum OPTS {
        ONE, FEW, OTHER
    }

    @NonNull
    private static OPTS decide(int hours) {
        if (hours == 0 || hours == 24) {
            // hotfix for "bylo půlnoc" -> "byla půlnoc"
            return OPTS.ONE;
        }
        int hoursMod = hours % 12;
        if (hoursMod == 1) {
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
    @NonNull
    public static String getHoursSoon(@NonNull Resources res, int hours) {
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
    @NonNull
    public static String getHoursItWas(@NonNull Resources res, int hours) {
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
            default:
                resourceId = R.string.it_was_other;
        }
        return res.getString(resourceId);
    }

}
