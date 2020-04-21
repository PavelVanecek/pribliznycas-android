package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class Tier6Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        instance = new Tier6();
        when(res.getString(R.string.almost_half))
                .thenReturn("Bude pul");
    }

    @Test
    public void testGetApproxTime() throws Exception {
        String expected = "Bude pul";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
