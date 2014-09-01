package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by cork on 25.04.14.
 */
public abstract class Tier implements ITier {

    protected SimpleDateFormat dateFormat;

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        return dateFormat.format(c.getTime());
    }

}
