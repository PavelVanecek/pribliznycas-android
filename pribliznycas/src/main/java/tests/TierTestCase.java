package tests;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.ITier;

/**
 * Created by cork on 25.04.14.
 */
public abstract class TierTestCase extends AndroidTestCase {

    protected ITier instance;
    protected Resources res;
    protected Calendar c;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        PackageManager pm = getContext().getPackageManager();
        this.res = pm.getResourcesForApplication(getContext().getPackageName());
        this.c = new GregorianCalendar(2014, 2, 28, 13, 25, 56);
    }

    public abstract void testGetApproxTime() throws Exception;
}
