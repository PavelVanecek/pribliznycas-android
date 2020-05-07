package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.helpers.MyTranslator;

/**
 * Created by cork on 27.05.14.
 */
public class Tier7 implements IDoubleTier {

    private static final int[] hours_str = Tier5.HOURS;

    private static final int[] quarter_hours = {
            R.string.to__midnight,
            R.string.to__one,
            R.string.to__two,
            R.string.to__three,
            R.string.to__four,
            R.string.to__five,
            R.string.to__six,
            R.string.to__seven,
            R.string.to__eight,
            R.string.to__nine,
            R.string.to__ten,
            R.string.to__eleven,
            R.string.to__noon,
            R.string.to__one,
            R.string.to__two,
            R.string.to__three,
            R.string.to__four,
            R.string.to__five,
            R.string.to__six,
            R.string.to__seven,
            R.string.to__eight,
            R.string.to__nine,
            R.string.to__ten,
            R.string.to__eleven,
            R.string.to__midnight
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

    @NonNull
    @Override
    public String[] getDoubleApproxTime(@NonNull Calendar c, @NonNull Resources res) {
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
            doubleApproxTime[1] = res.getString(quarter_hours[transcriptedHours]);
        } else if (minutes <= 40) {
            doubleApproxTime[0] = res.getString(R.string.half_to);
            doubleApproxTime[1] = res.getString(half_hours[hours + 1]);
        } else if (minutes <= 50) {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = (hours % 12) + 1;
            doubleApproxTime[0] = res.getString(R.string.three_quarters_);
            doubleApproxTime[1] = res.getString(quarter_hours[transcriptedHours]);
        } else /* if (minutes > 51 && minutes < 8) */ {
            // "ctvrt na dvanact" versus "bude pulnoc"
            transcriptedHours = hours + 1;
            doubleApproxTime[0] = MyTranslator.getHoursSoon(res, transcriptedHours);
            doubleApproxTime[1] = res.getString(hours_str[transcriptedHours]);
        }
        return doubleApproxTime;
    }
}
