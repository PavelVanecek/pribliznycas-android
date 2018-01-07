package pribliznycas;

import android.content.res.Resources;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.tier.ITier;

/**
 * Created by cork on 25.04.14.
 */
public abstract class TierTestCase {

    ITier instance;
    Resources res;
    Calendar c;

    protected void setUp() throws Exception {
        this.c = new GregorianCalendar(2014, 2, 28, 13, 25, 56);
    }

}
