package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class Tier1Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        instance = new Tier1();
    }

    @Test
    public void testGetApproxTime() {
        String actual = instance.getApproxTime(c, res);
        String expected = "2014";
        assertEquals(expected, actual);
    }
}
