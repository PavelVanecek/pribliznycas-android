package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier4 implements ITier {

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        int hours = c.get(Calendar.HOUR_OF_DAY);
        if (6 <= hours && hours < 9) {
            return res.getString(R.string.morning);
        }
        if (9 <= hours && hours < 11) {
            return res.getString(R.string.beforenoon);
        }
        if (11 <= hours && hours < 13) {
            return res.getString(R.string.lunchtime);
        }
        if (13 <= hours && hours < 18) {
            return res.getString(R.string.afternoon);
        }
        if (18 <= hours && hours < 22) {
            return res.getString(R.string.evening);
        }
        return res.getString(R.string.night);
    }
}
