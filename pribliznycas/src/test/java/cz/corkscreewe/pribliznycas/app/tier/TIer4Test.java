package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class TIer4Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        instance = new Tier4();
        when(res.getString(R.string.afternoon))
                .thenReturn("odpoledne");
    }

    @Test
    public void testGetApproxTime() {
        String expected = "odpoledne";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
