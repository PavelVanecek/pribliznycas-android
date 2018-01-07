package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by vanecekp on 07/01/2018.
 */

public class Tier25 implements ITier {

    private static final int[] DAYS = {
            R.string.midnight, // bogus string on index 0
            R.string.month_1,
            R.string.month_2,
            R.string.month_3,
            R.string.month_4,
            R.string.month_5,
            R.string.month_6,
            R.string.month_7,
            R.string.month_8,
            R.string.month_9,
            R.string.month_10,
            R.string.month_11,
            R.string.month_12,
            R.string.month_13,
            R.string.month_14,
            R.string.month_15,
            R.string.month_16,
            R.string.month_17,
            R.string.month_18,
            R.string.month_19,
            R.string.month_20
    };

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        int days = c.get(Calendar.DAY_OF_MONTH);
        if (days > 30) {
            return
                res.getString(R.string.month_30) +
                " " +
                res.getString(DAYS[days - 30]
            );
        }
        if (days == 30) {
            return res.getString(R.string.month_30);
        }
        if (days > 20) {
            return
                res.getString(R.string.month_20) +
                " " +
                res.getString(DAYS[days - 20]
            );
        }
        return res.getString(DAYS[days]);
    }
}
