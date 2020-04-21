package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by cork on 25.04.14.
 */
public abstract class Tier implements ITier {

    SimpleDateFormat dateFormat;

    @Override
    public String getApproxTime(@NonNull Calendar c, Resources res) {
        return dateFormat.format(c.getTime());
    }

}
