package pribliznycas;

import org.junit.Test;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.tier.Tier4;

import static junit.framework.Assert.assertEquals;

/**
 * Created by cork on 25.04.14.
 */
public class TIer4Test extends TierTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new Tier4();
    }

    @Test
    public void testGetApproxTime() throws Exception {
        assertEquals(res.getString(R.string.afternoon), instance.getApproxTime(c, res));
    }
}
