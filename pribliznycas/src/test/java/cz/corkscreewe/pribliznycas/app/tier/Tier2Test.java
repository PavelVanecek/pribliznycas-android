package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class Tier2Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        when(res.getStringArray(R.array.months))
                .thenReturn(new String[]{"1", "2", "3"});
        instance = new Tier2();
    }

    @Test
    public void testGetApproxTime() {
        String expected = "3";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
