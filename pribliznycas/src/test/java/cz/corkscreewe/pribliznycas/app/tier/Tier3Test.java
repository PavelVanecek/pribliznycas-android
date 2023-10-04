package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class Tier3Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        instance = new Tier3();
        when(res.getStringArray(R.array.daysoftheweek))
                .thenReturn(new String[]{"1", "2", "3", "4", "5", "6", "7"});
    }

    @Test
    public void testGetApproxTime() {
        String expected = "6";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
