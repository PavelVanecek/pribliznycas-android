package cz.corkscreewe.pribliznycas.app.tiers;

import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cz.corkscreewe.pribliznycas.app.ITier;
import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.Tier;

/**
 * Created by cork on 25.04.14.
 */
public class Tier1 extends Tier {

    public Tier1() {
        dateFormat = new SimpleDateFormat("yyyy");
    }

}
