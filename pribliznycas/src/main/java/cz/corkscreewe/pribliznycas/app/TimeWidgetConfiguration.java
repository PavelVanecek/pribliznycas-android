//package cz.corkscreewe.pribliznycas.app;
//
//import android.app.Activity;
//import android.appwidget.AppWidgetManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.preference.Preference;
//import android.preference.PreferenceFragment;
//import android.preference.PreferenceManager;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.View;
//
///**
// * Created by cork on 04.05.14.
// */
//public class TimeWidgetConfiguration extends FragmentActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
//
//    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//    private static final int FONT_DIALOG_REQUEST_CODE = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        // if the user presses BACK or cancelled the activity, then we should not add app widget
//        // setResult(RESULT_CANCELED);
//        // actually, we don't want that behaviour
//
//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//                finish();
//            }
//
//            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//            sharedPref.registerOnSharedPreferenceChangeListener(this);
//
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(android.R.id.content, new WidgetPrefFragment())
//                    .commit();
//
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        sharedPref.unregisterOnSharedPreferenceChangeListener(this);
//        super.onDestroy();
//    }
//
//    private void configureWidget(int appWidgetId) {
//        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//            finish();
//        }
//        Intent result = new Intent();
//        result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        setResult(RESULT_OK, result);
//        finish();
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        Log.d("preference", "key changed: " + key);
//    }
//
//    private class WidgetPrefFragment extends PreferenceFragment {
//
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            if (requestCode == FONT_DIALOG_REQUEST_CODE) {
//                if (resultCode == RESULT_OK) {
//                    int color = data.getIntExtra(ColorDialog.COLOR, 0);
//                    Log.d("preference", "color changed to " + color);
//                    Preference preference_font_color_button = findPreference("preference_font_color_button_key");
//                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TimeWidgetConfiguration.this);
//                    sharedPreferences
//                            .edit()
//                            .putInt(preference_font_color_button.getKey(), color)
//                            .commit();
//                }
//            }
//        }
//
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            final Context context = TimeWidgetConfiguration.this;
//            addPreferencesFromResource(R.xml.preferences);
//
//            Preference preference_done_button = findPreference("preference_done_button_key");
//            if (preference_done_button != null) {
//                preference_done_button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                    @Override
//                    public boolean onPreferenceClick(Preference preference) {
//                        configureWidget(appWidgetId);
//                        return true;
//                    }
//                });
//            }
//
//            Preference preference_font_color_button = findPreference("preference_font_color_button_key");
//            if (preference_font_color_button != null) {
//                preference_font_color_button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                    @Override
//                    public boolean onPreferenceClick(Preference preference) {
//                        Intent intent = new Intent(context, ColorDialog.class);
//                        startActivityForResult(intent, FONT_DIALOG_REQUEST_CODE);
//                        return true;
//                    }
//                });
//            }
//        }
//    }
//
//}
