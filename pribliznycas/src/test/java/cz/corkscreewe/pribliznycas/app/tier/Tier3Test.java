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
        when(res.getString(R.string.friday))
                .thenReturn("Patek");
    }

    @Test
    public void testGetApproxTime() {
        String expected = "Patek";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
