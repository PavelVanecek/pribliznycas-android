package tests;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tiers.Tier6;

/**
 * Created by cork on 25.04.14.
 */
public class Tier6Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier6();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals(res.getString(R.string.almost_half), instance.getApproxTime(c, res));
    }
}
