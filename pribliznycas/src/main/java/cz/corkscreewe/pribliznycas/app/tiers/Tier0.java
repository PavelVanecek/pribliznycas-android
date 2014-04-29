package cz.corkscreewe.pribliznycas.app.tiers;

import android.content.res.Resources;

import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.ITier;
import cz.corkscreewe.pribliznycas.app.R;

/**
 * Created by cork on 25.04.14.
 */
public class Tier0 implements ITier {

    @Override
    public String getApproxTime(Calendar c, Resources res) {
        return res.getString(R.string.sometimes);
    }
}
