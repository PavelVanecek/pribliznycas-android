package tests;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tiers.Tier0;

/**
 * Created by cork on 25.04.14.
 */
public class Tier0Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.instance = new Tier0();
    }

    public void testGetApproxTime() throws Exception {
        assertEquals(this.res.getString(R.string.sometimes), instance.getApproxTime(c, res));
    }
}
