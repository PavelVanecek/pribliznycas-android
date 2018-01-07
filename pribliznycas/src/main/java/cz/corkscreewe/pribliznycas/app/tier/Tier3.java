package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier3 extends Tier {

    private final int[] arr = {
            -1,
            R.string.sunday,
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday
    };

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        int days = c.get(Calendar.DAY_OF_WEEK);
        return res.getString(arr[days]);
    }
}
