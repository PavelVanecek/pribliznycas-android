package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(MockitoJUnitRunner.class)
public abstract class TierTestCase {

    ITier instance;
    @Mock Resources res;
    Calendar c;

    void setUp() {
        this.c = new GregorianCalendar(2014, 2, 28, 13, 25, 56);
    }

}
