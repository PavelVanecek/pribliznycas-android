package cz.corkscreewe.pribliznycas.app.tier;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by cork on 25.04.14.
 */
public class Tier1 extends Tier {

    public Tier1() {
        dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    }

}
