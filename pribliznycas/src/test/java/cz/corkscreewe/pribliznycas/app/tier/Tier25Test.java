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
        instance = new Tier25();
    }

    private void prepare(int day) {
        c = new GregorianCalendar(2018, 0, day, 1, 1, 1);
    }

    @Test
    public void test01() {
        String expected = "prvniho";
        when(res.getString(R.string.month_1)).thenReturn(expected);
        prepare(1);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test10() {
        String expected = "desateho";
        when(res.getString(R.string.month_10)).thenReturn(expected);
        prepare(10);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test11() {
        String expected = "jedenacteho";
        when(res.getString(R.string.month_11)).thenReturn(expected);
        prepare(11);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test19() {
        String expected = "devatena";
        when(res.getString(R.string.month_19)).thenReturn(expected);
        prepare(19);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test20() {
        String expected = "dvacet";
        when(res.getString(R.string.month_20)).thenReturn(expected);
        prepare(20);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test21() {
        String expected = "dvacateho prvniho";
        when(res.getString(R.string.month_20)).thenReturn("dvacateho");
        when(res.getString(R.string.month_1)).thenReturn("prvniho");
        prepare(21);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test29() {
        String expected = "dvacateho devateho";
        when(res.getString(R.string.month_20)).thenReturn("dvacateho");
        when(res.getString(R.string.month_9)).thenReturn("devateho");
        prepare(29);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test30() {
        String expected = "tricateho";
        when(res.getString(R.string.month_30)).thenReturn("tricateho");
        prepare(30);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

    @Test
    public void test31() {
        String expected = "tricateho prvniho";
        when(res.getString(R.string.month_30)).thenReturn("tricateho");
        when(res.getString(R.string.month_1)).thenReturn("prvniho");
        prepare(31);
        String actual = instance.getApproxTime(c, res);
        assertEquals(expected, actual);
    }

}
