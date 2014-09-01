package tests;

import cz.corkscreewe.pribliznycas.app.tier.Tier3;

/**
 * Created by cork on 25.04.14.
 */
public class Tier3Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier3();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals("Friday", instance.getApproxTime(c, res));
    }
}
