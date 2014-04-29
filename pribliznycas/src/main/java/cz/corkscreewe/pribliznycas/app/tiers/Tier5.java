package cz.corkscreewe.pribliznycas.app.tiers;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.ITier;
import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier5 implements ITier {

    private int[] arr = {
            R.string.midnight,
            R.string.hours_one,
            R.string.hours_two,
            R.string.hours_three,
            R.string.hours_four,
            R.string.hours_five,
            R.string.hours_six,
            R.string.hours_seven,
            R.string.hours_eight,
            R.string.hours_nine,
            R.string.hours_ten,
            R.string.hours_eleven,
            R.string.hours_noon,
            R.string.hours_one,
            R.string.hours_two,
            R.string.hours_three,
            R.string.hours_four,
            R.string.hours_five,
            R.string.hours_six,
            R.string.hours_seven,
            R.string.hours_eight,
            R.string.hours_nine,
            R.string.hours_ten,
            R.string.hours_eleven
    };

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        if (minutes > 45) {
            hours = (hours + 1) % 24;
        }
        return res.getString(arr[hours]);
    }
}
