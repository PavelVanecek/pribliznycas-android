package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;

import java.util.Calendar;

/**
 * Created by cork on 25.04.14.
 */
public interface ITier {

    String getApproxTime(Calendar c, Resources res);

}
