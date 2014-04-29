package tests;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tiers.Tier4;

/**
 * Created by cork on 25.04.14.
 */
public class TIer4Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier4();
    }

    @Override
    public void testGetApproxTime() throws Exception {
        assertEquals(res.getString(R.string.afternoon), instance.getApproxTime(c, res));
    }
}
