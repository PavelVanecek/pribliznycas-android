package tests;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.Tier5;

/**
 * Created by cork on 25.04.14.
 */
public class Tier5Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier5();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals(res.getString(R.string.hours_one), instance.getApproxTime(c, res));
    }
}
