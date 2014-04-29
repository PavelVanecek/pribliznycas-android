//package cz.corkscreewe.pribliznycas.app;
//
//import android.appwidget.AppWidgetManager;
//import android.content.BroadcastReceiver;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.RemoteViews;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//public class MainActivity extends ActionBarActivity {
//
//    // command to announce that tier option has changed
//    public static final String SEEKBAR_PROGRESS_CHANGE = "cz.corkscreewe.pribliznycas.SEEKBAR_PROGRESS_ACTION";
//
//    private boolean progressTouchActive = false;
//    BroadcastReceiver receiver;
//    IntentFilter intentFilter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        intentFilter = new IntentFilter(TimeService.ACTION_TIME_CHANGE);
//        receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("activity", "received intent " + intent.getAction());
//                Bundle extras = intent.getExtras();
//                if (extras != null) {
//                    int tier = extras.getInt("tier");
//                    String time = extras.getString("time");
//                    render(tier, time);
//                }
//            }
//        };
//
//        registerReceiver(receiver, intentFilter);
//        setupSeekBar(getApplicationContext());
//        startService(new Intent(this, TimeService.class));
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        startService(new Intent(this, TimeService.class));
//        registerReceiver(receiver, intentFilter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        startService(new Intent(this, TimeService.class));
//        registerReceiver(receiver, intentFilter);
//    }
//
//    /**
//     * Renders activity + widget
//     * @param tier
//     * @param time
//     */
//    private void render(int tier, String time) {
//        renderActivity(time);
//        renderWidget(time);
//        setSeekBarTier(tier);
//    }
//
//    private void renderActivity(String time) {
//        TextView tv = (TextView) findViewById(R.id.time);
//        tv.setText(time);
//    }
//
//    private void renderWidget(String time) {
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.time_widget_buttons);
//        remoteViews.setTextViewText(R.id.appwidget_text, time);
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//        ComponentName componentName = new ComponentName(
//            getPackageName(),
//            TimeWidget.class.getName()
//        );
//        if (appWidgetManager != null) {
//            appWidgetManager.updateAppWidget(componentName, remoteViews);
//        }
//    }
//
//    private void setSeekBarTier(int tier) {
//        if (!progressTouchActive) {
//            SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
//            seekBar.setProgress(100 * tier / 6);
//        }
//    }
//
//    private void setTier(Context context, int tier) {
//        Intent intent = new Intent(SEEKBAR_PROGRESS_CHANGE);
//        intent.putExtra("tier", tier);
//        sendBroadcast(intent);
////        Log.d("activity", "sending seekbar intent");
//    }
//
//    private void setupSeekBar(final Context context) {
//        int default_position = 4;
//        final float tiers = 6;
//        final int[] discrete = {default_position};
//        float start_position = 100 * default_position / tiers;
//        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setProgress((int) start_position);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                float fProgress = progress;
//                int currentPosition = (int) (((fProgress / 100) * tiers) + 0.5);
//                if (discrete[0] != currentPosition) {
//                    discrete[0] = currentPosition;
//                    setTier(context, discrete[0]);
//                }
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                progressTouchActive = true;
////                Log.d("progress", "start");
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                setSeekBarTier(discrete[0]);
//                progressTouchActive = false;
////                Log.d("progress", "stop");
//            }
//        });
//    }
//
//}
