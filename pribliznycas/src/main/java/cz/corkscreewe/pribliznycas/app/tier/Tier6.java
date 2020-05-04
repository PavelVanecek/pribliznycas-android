package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier6 implements ITier {

    private final int[] arr = {
            R.string.full_and_a_little,
            R.string.close_to_quarter_past,
            R.string.quarter_past,
            R.string.after_quarter_past,
            R.string.almost_half,
            R.string.half,
            R.string.after_half,
            R.string.almost_quarter_to,
            R.string.quarter_to,
            R.string.after_quarter_to,
            R.string.almost_full,
            R.string.full
    };

    @NonNull
    @Override
    public String getApproxTime(@NonNull Calendar c, @NonNull Resources res) {
        float length = arr.length;
        float minutes = c.get(Calendar.MINUTE);
        int index = (int) (((minutes / 60) * length) - 0.5);

        if (index < 0) index += length;

        return res.getString(arr[index]);
    }
}
