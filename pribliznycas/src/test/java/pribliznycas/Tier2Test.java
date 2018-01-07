package pribliznycas;

import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.tier.Tier2;

import static junit.framework.Assert.assertEquals;

/**
 * Created by cork on 25.04.14.
 */
public class Tier2Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier2();
    }

    @Test
    public void testGetApproxTime() throws Exception {
        assertEquals("March", instance.getApproxTime(c, res));
    }
}
