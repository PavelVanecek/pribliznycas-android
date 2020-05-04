package cz.corkscreewe.pribliznycas.app.tier;

import android.content.res.Resources;
import androidx.annotation.NonNull;

import java.util.Calendar;

/**
 * Created by cork on 29.05.14.
 */
public interface IDoubleTier {

    @NonNull
    String[] getDoubleApproxTime(Calendar c, Resources res);

}
