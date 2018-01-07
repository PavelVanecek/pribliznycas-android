package pribliznycas;

import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.Tier0;

import static junit.framework.Assert.assertEquals;

/**
 * Created by cork on 25.04.14.
 */
public class Tier0Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.instance = new Tier0();
    }

    @Test
    public void testGetApproxTime() throws Exception {
        int sometimes = R.string.sometimes;
        String expected = this.res.getString(sometimes);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
