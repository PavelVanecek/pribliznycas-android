package tests;

import cz.corkscreewe.pribliznycas.app.tiers.Tier2;

/**
 * Created by cork on 25.04.14.
 */
public class Tier2Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier2();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals("March", instance.getApproxTime(c, res));
    }
}
