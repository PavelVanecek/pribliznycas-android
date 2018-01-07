package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.helpers.MyTranslator;

/**
 * Created by cork on 27.05.14.
 */
public class Tier7 implements IDoubleTier {

    private static final int[] hours_str = Tier5.HOURS;

    private static final int[] quarter_hours = {
            R.string.midnight,
            R.string.quarter_to_one,
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
            R.string.quarter_to_one,
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
            R.string.midnight
    };

    private static final int[] half_hours = {
            R.string.half_to_twelve,
            R.string.half_to_one,
            R.string.half_to_two,
            R.string.half_to_three,
            R.string.half_to_four,
            R.string.half_to_five,
            R.string.half_to_six,
            R.string.half_to_seven,
            R.string.half_to_eight,
            R.string.half_to_nine,
            R.string.half_to_ten,
            R.string.half_to_eleven,
            R.string.half_to_twelve,
            R.string.half_to_one,
            R.string.half_to_two,
            R.string.half_to_three,
            R.string.half_to_four,
            R.string.half_to_five,
            R.string.half_to_six,
            R.string.half_to_seven,
            R.string.half_to_eight,
            R.string.half_to_nine,
            R.string.half_to_ten,
            R.string.half_to_eleven,
            R.string.half_to_twelve
    };

    @Override
    public String[] getDoubleApproxTime(Calendar c, Resources res) {
        int hours = c.get(Calendar.HOUR_OF_DAY); // 0 - indexed
        int minutes = c.get(Calendar.MINUTE);
        String[] doubleApproxTime = new String[2];
        int transcriptedHours;
        if (minutes <= 8) {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = hours;
            doubleApproxTime[0] = MyTranslator.getHoursItWas(res, transcriptedHours);
            doubleApproxTime[1] = res.getString(hours_str[transcriptedHours]);
        } else if (minutes <= 21) {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = (hours % 12) + 1;
            doubleApproxTime[0] = res.getString(R.string.quarter_);
            doubleApproxTime[1] = res.getString(R.string.to__) + res.getString(quarter_hours[transcriptedHours]);
        } else if (minutes <= 40) {
            doubleApproxTime[0] = res.getString(R.string.half_to);
            doubleApproxTime[1] = res.getString(half_hours[hours + 1]);
        } else if (minutes <= 50) {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = (hours % 12) + 1;
            doubleApproxTime[0] = res.getString(R.string.three_quarters_);
            doubleApproxTime[1] = res.getString(R.string.to__) + res.getString(quarter_hours[transcriptedHours]);
        } else /* if (minutes > 51 && minutes < 8) */ {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = hours + 1;
            doubleApproxTime[0] = MyTranslator.getHoursSoon(res, transcriptedHours);
            doubleApproxTime[1] = res.getString(hours_str[transcriptedHours]);
        }
        return doubleApproxTime;
    }
}
