package pribliznycas;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.Tier5;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class Tier5Test extends TierTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        instance = new Tier5();
        when(res.getString(R.string.hours_one))
            .thenReturn("Jedna");
    }

    @Test
    public void testGetApproxTime() throws Exception {
        String expected = "Jedna";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
