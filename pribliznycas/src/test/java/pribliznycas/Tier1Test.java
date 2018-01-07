package pribliznycas;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.tier.Tier1;

import static junit.framework.Assert.assertEquals;

public class Tier1Test extends TierTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new Tier1();
    }

    @Test
    public void testGetApproxTime() throws Exception {
        String actual = instance.getApproxTime(c, res);
        String expected = "2014";
        assertEquals(expected, actual);
    }
}
