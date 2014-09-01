package tests;

import cz.corkscreewe.pribliznycas.app.tier.Tier1;

/**
 * Created by cork on 25.04.14.
 */
public class Tier1Test extends TierTestCase {

    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier1();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals("2014", instance.getApproxTime(c, res));
    }
}
