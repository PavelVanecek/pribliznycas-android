//package cz.corkscreewe.pribliznycas.app;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.larswerkman.holocolorpicker.ColorPicker;
//import com.larswerkman.holocolorpicker.OpacityBar;
//import com.larswerkman.holocolorpicker.SVBar;
//
///**
// * Created by cork on 04.05.14.
// */
//public class ColorDialog extends Activity implements ColorPicker.OnColorChangedListener {
//
//    public static final String COLOR = "color";
//
//    private ColorPicker picker;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.color_dialog);
//
//        setResult(RESULT_CANCELED);
//
//        picker = (ColorPicker) findViewById(R.id.picker);
////        SVBar svBar = (SVBar) findViewById(R.id.svbar);
//        OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
//        Button btnCancel = (Button) findViewById(R.id.color_picker_button_cancel);
//        Button btnOk = (Button) findViewById(R.id.color_picker_button_ok);
//
////        picker.addSVBar(svBar);
//        picker.addOpacityBar(opacityBar);
//        picker.setOnColorChangedListener(this);
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                picker.setOldCenterColor(picker.getColor());
//                Intent result = new Intent();
//                result.putExtra(COLOR, picker.getColor());
//                setResult(RESULT_OK, result);
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public void onColorChanged(int i) {
////        Log.v("colorDialog", "color changed to " + i);
//    }
//
//    /*@Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        if (getActivity() == null) {
//            return null;
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.id.dialog_pick_color_title);
//
//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        builder.setView(layoutInflater.inflate(R.layout.color_dialog, null))
//                .setPositiveButton(R.string.dialog_pick_color_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // TODO potvrdit barvu
//                    }
//                })
//                .setNegativeButton(R.string.dialog_pick_color_cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        try {
//                            ColorDialog.this.getDialog().cancel();
//                        } catch (NullPointerException npe) {
//                            // whatever
//                        }
//                    }
//                });
//                    return builder.create();
//                }*/
//    }
