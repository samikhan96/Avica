package com.example.myapplication.AvicaPatient.VivaLink;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.example.myapplication.AvicaPatient.R;
import com.vivalnk.sdk.demo.base.app.ConnectedActivity;
import com.vivalnk.sdk.demo.base.app.Layout;
import com.vivalnk.sdk.widget.CustomEcgView;

import top.defaults.colorpicker.ColorPickerPopup;

public class CustomEcgViewActivity extends ConnectedActivity {

    private static final String TAG = "CustomEcgViewActivity";

    private CustomEcgView rtsEcgView;
    private Button btnSwitchGain;
    private Button btnRevert;
    private Button btnSetGridColor;
    private Button btnSetWaveformStrokeColor;
    private Button btnSetWaveformStrokeWidth;
    private Button btnSetPaperSpeed;

    private volatile boolean revert;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Find views
        rtsEcgView = findViewById(R.id.rtsEcgView);
        btnSwitchGain = findViewById(R.id.btnSwitchGain);
        btnRevert = findViewById(R.id.btnRevert);
        btnSetGridColor = findViewById(R.id.btnSetGridColor);
        btnSetWaveformStrokeColor = findViewById(R.id.btnSetWaveformStrokeColor);
        btnSetWaveformStrokeWidth = findViewById(R.id.btnSetWaveformStrokeWidth);
        btnSetPaperSpeed = findViewById(R.id.btnSetPaperSpeed);

        // Set up ECG view
        btnRevert.setText(revert ? "De-Revert" : "Revert");
        rtsEcgView.setup(mDevice);

        // Set click listeners
        btnRevert.setOnClickListener(v -> {
            revert = !revert;
            rtsEcgView.revert(revert);
            btnRevert.setText(revert ? "De-Revert" : "Revert");
        });

        btnSwitchGain.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Switch ECG Gain")
                    .setItems(R.array.ecg_gains, (dialog, which) -> {
                        int gain;
                        if (which == 0) gain = 5;
                        else if (which == 1) gain = 10;
                        else gain = 20;
                        rtsEcgView.switchGain(gain);
                    });
            builder.create().show();
        });

        btnSetGridColor.setOnClickListener(v -> {
            new ColorPickerPopup.Builder(CustomEcgViewActivity.this).initialColor(Color.RED) // set initial color
                    .enableBrightness(true) // enable color brightness
                    .enableAlpha(true) // enable color alpha
                    .okTitle("Choose") // this is top right
                    .cancelTitle("Cancel") // this is top left
                    .showIndicator(true) // this is the small box
                    .showValue(true) // this is the value which
                    .build()
                    .show(
                            v,
                            new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void
                                onColorPicked(int color) {
                                    rtsEcgView.setGridColor(color);
                                }
                            });

        });

        btnSetWaveformStrokeColor.setOnClickListener(v -> {
            new ColorPickerPopup.Builder(CustomEcgViewActivity.this).initialColor(Color.RED) // set initial color
                    .enableBrightness(true) // enable color brightness
                    .enableAlpha(true) // enable color alpha
                    .okTitle("Choose") // this is top right
                    .cancelTitle("Cancel") // this is top left
                    .showIndicator(true) // this is the small box
                    .showValue(true) // this is the value which
                    .build()
                    .show(
                            v,
                            new ColorPickerPopup.ColorPickerObserver() {
                                @Override
                                public void
                                onColorPicked(int color) {
                                    rtsEcgView.setEcgWaveformStrokeColor(color);
                                }
                            });
        });

        btnSetWaveformStrokeWidth.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set Waveform Stroke Width")
                    .setItems(R.array.ecg_wave_stroke_width, (dialog, which) -> {
                        rtsEcgView.setEcgWaveformStrokeWidth(which + 1);
                    });
            builder.create().show();
        });

        btnSetPaperSpeed.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Set ECG Paper Speed")
                    .setItems(R.array.ecg_paper_speed, (dialog, which) -> {
                        float speed = 25;
                        if (which == 0) speed = 12.5f;
                        else if (which == 1) speed = 25;
                        else if (which == 2) speed = 50;
                        else speed = 100;
                        rtsEcgView.setPaperSpeed(speed);
                    });
            builder.create().show();
        });
    }

    @Override
    protected Layout getLayout() {
        return Layout.createLayoutByID(R.layout.activity_custom_ecg_view);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rtsEcgView.destroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
