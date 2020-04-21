package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier0 implements ITier {

    @NonNull
    @Override
    public String getApproxTime(Calendar c, @NonNull Resources res) {
        return res.getString(R.string.sometimes);
    }
}
