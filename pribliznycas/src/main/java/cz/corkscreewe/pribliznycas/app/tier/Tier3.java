package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier3 extends Tier {
    @NonNull
    @Override
    public String getApproxTime(@NonNull Calendar c, @NonNull Resources res) {
        int days = c.get(Calendar.DAY_OF_WEEK);
        return res.getStringArray(R.array.daysoftheweek)[days - 1];
    }
}
