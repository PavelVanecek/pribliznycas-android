package cz.corkscreewe.pribliznycas.app.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import cz.corkscreewe.pribliznycas.app.R;
import cz.corkscreewe.pribliznycas.app.service.SingleTimeService;

/**
 * Created by cork on 29.05.14.
 */
public class SingleTimeWidget extends TimeWidget {

    @Override
    protected String setText(RemoteViews remoteViews, Bundle intentExtras) {
        String time = intentExtras.getString("time");
        Log.i("single widget", time);
        remoteViews.setTextViewText(R.id.appwidget_text, time);
        return time;
    }

    @Override
    protected int getHomescreenLayoutId() {
        return R.layout.single_time_widget;
    }

    @Override
    protected int getKeyguardLayoutId() {
        return R.layout.time_widget_keyguard;
    }

    @Override
    protected Intent getServiceIntent(Context context, int appWidgetId) {
        Intent intent;
        intent = new Intent(context, SingleTimeService.class);
        Log.d("single time widget", "SingleTimeService intent created");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        return intent;
    }
}
