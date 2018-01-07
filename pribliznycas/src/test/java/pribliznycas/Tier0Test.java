package pribliznycas;

import org.junit.Before;
import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.Tier0;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by cork on 25.04.14.
 */
public class Tier0Test extends TierTestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        this.instance = new Tier0();
        when(res.getString(R.string.sometimes))
                .thenReturn("nekdy");
    }

    @Test
    public void testGetApproxTime() throws Exception {
        int sometimes = R.string.sometimes;
        String expected = "nekdy";
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
