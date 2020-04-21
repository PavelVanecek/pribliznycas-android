package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier2 implements ITier {
    private final int[] arr = {
            R.string.january,
            R.string.february,
            R.string.march,
            R.string.april,
            R.string.may,
            R.string.june,
            R.string.july,
            R.string.august,
            R.string.september,
            R.string.october,
            R.string.november,
            R.string.december,
            R.string.undecember
    };

    @NonNull
    @Override
    public String getApproxTime(@NonNull Calendar c, @NonNull Resources res) {
        int month = c.get(Calendar.MONTH);
        return res.getString(arr[month]);
    }

}
