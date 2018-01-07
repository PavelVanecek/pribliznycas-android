package pribliznycas;

import android.content.res.Resources;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.tier.ITier;

@RunWith(MockitoJUnitRunner.class)
public abstract class TierTestCase {

    ITier instance;
    @Mock Resources res;
    Calendar c;

    protected void setUp() throws Exception {
        this.c = new GregorianCalendar(2014, 2, 28, 13, 25, 56);
    }

}
