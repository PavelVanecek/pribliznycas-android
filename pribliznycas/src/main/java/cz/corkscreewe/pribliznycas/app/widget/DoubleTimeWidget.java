package cz.corkscreewe.pribliznycas.app.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.service.DoubleTimeService;
import cz.corkscreewe.pribliznycas.app.service.SingleTimeService;

/**
 * Created by cork on 29.05.14.
 */
public class DoubleTimeWidget extends TimeWidget {

    @Override
    protected String setText(RemoteViews remoteViews, Bundle intentExtras) {
        String[] times = intentExtras.getStringArray(DoubleTimeService.DOUBLE_TIME_EXTRA);
        Log.i("double widget", times[0] + ", " + times[1]);
        remoteViews.setTextViewText(R.id.double_appwidget_text_up, times[0]);
        remoteViews.setTextViewText(R.id.double_appwidget_text_bottom, times[1]);
        return times[0] + " " + times[1];
    }

    @Override
    protected int getHomescreenLayoutId() {
        return R.layout.double_time_widget_buttons;
    }

    @Override
    protected int getKeyguardLayoutId() {
        return R.layout.double_time_widget_keyguard;
    }

    @Override
    protected Intent getServiceIntent(Context context, int appWidgetId) {
        Intent intent;
        intent = new Intent(context, DoubleTimeService.class);
        Log.d("double time widget", "DoubleTimeService intent created");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return intent;
    }
}
