package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by vanecekp on 07/01/2018.
 */

public class Tier25 implements ITier {
    @NonNull
    @Override
    public String getApproxTime(@NonNull Calendar c, @NonNull Resources res) {
        int days = c.get(Calendar.DAY_OF_MONTH);
        return res.getStringArray(R.array.daysofthemonth)[days - 1];
    }
}
