package cz.corkscreewe.pribliznycas.app.tier;

import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;

import cz.corkscreewe.pribliznycas.app.R;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by vanecekp on 07/01/2018.
 */
public class Tier25Test extends TierTestCase {

    @Before
    public void setUp() {
        super.setUp();
        when(res.getStringArray(R.array.daysofthemonth)).thenReturn(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        instance = new Tier25();
    }

    private void prepare(int day) {
        c = new GregorianCalendar(2018, 0, day, 1, 1, 1);
    }

    @Test
    public void test01() {
        String expected = "1";
        prepare(1);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test10() {
        String expected = "10";
        prepare(10);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }
}
