package com.example.myapplication.AvicaPatient.VivaLink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.tencent.mmkv.MMKV;
import com.vivalnk.sdk.BuildConfig;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.demo.base.app.BaseToolbarActivity;
import com.vivalnk.sdk.demo.base.app.Layout;
import com.vivalnk.sdk.demo.repository.device.DeviceManager;

public class ConfigActivity extends AppCompatActivity {

    private boolean isFirst=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        initView();
        setListener();
        if (com.vivalnk.sdk.BuildConfig.sdkChannel.equals("sdk01")) {
            DeviceManager.getInstance().setDataReceiveListener(new MyDataReceiveListener());
        } else {
            DeviceManager.getInstance().setDeviceStatusListener(new MyDeviceStatusListener());
        }
    }

    private void initView() {

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void doShowToast(String msg){
        if(isFirst){
            return;
        }
        AppUtils.Toast(msg);
    }

    private void setListener(){
        CheckBox radio_auto_connect_last=findViewById(R.id.radio_auto_connect_last);
        radio_auto_connect_last.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().allowConnectLastConnectedDevice=isChecked;
            doShowToast("isChecked="+isChecked);
        });
        radio_auto_connect_last.setChecked(true);

        CheckBox radio_auto_connect=findViewById(R.id.radio_auto_connect);
        radio_auto_connect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().autoConnectIfDisconnectDevice=isChecked;
            doShowToast("isChecked="+isChecked);
        });
        radio_auto_connect.setChecked(true);


        CheckBox radio_log_output=findViewById(R.id.radio_log_output);
        radio_log_output.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().allowWriteToFile(isChecked);
            doShowToast("isChecked="+isChecked);
        });
        radio_log_output.setChecked(true);

        CheckBox radio_ecg_sync_clock=findViewById(R.id.radio_ecg_sync_clock);
        radio_ecg_sync_clock.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().allowForceClockSyncOnceConnected=isChecked;
            doShowToast("isChecked="+isChecked);
        });
        radio_ecg_sync_clock.setChecked(true);


        CheckBox radio_blooddev_read_file=findViewById(R.id.radio_blooddev_read_file);
        radio_blooddev_read_file.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().allowCheckme_02ReadFile=isChecked;
            doShowToast("isChecked="+isChecked);
        });
        radio_blooddev_read_file.setChecked(true);

        TextView tv_data_upload = findViewById(R.id.tv_data_upload);
        CheckBox radio_data_upload=findViewById(R.id.radio_data_upload);
        if (BuildConfig.sdkChannel.equals("sdk01")) {
            radio_data_upload.setOnCheckedChangeListener((buttonView, isChecked) -> {
                DeviceManager.getInstance().allowUploadDataToCloud=isChecked;
                doShowToast("isChecked="+isChecked);
                if (isChecked) {
                    showRegisterSubjectDialog();
                }
            });
            radio_data_upload.setChecked(false);
        } else {
            DeviceManager.getInstance().allowUploadDataToCloud = true;
            radio_data_upload.setEnabled(false);
            radio_data_upload.setChecked(true);
            tv_data_upload.setText(getResources().getString(R.string.config_data_upload_msg));
            showRegisterSubjectDialog();
        }

        CheckBox radio_heart_rate=findViewById(R.id.radio_heart_rate);
        radio_heart_rate.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DeviceManager.getInstance().allowAnimalHrAlgo=isChecked;
            doShowToast("isChecked="+isChecked);
        });
        radio_heart_rate.setChecked(false);

        EditText et_blood_rtsupload_rate=findViewById(R.id.et_blood_rtsupload_rate);
        et_blood_rtsupload_rate.setText("4");

        findViewById(R.id.bt_continue).setOnClickListener(v -> {
            float interval=4;
            try {
                interval=Float.valueOf(et_blood_rtsupload_rate.getText().toString().trim());
            }catch (Exception e){
                e.printStackTrace();
            }

            DeviceManager.getInstance().checkme_o2RealtimeDataInterval=interval;

            DeviceManager.getInstance().init(this.getApplication());

            startActivity(ScanningActivity.newIntent(ConfigActivity.this));
            finish();
        });

        isFirst=false;

    }

    /**
     * 修改projectId和subjectId dialog
     */
    private void showRegisterSubjectDialog() {
        if (MMKV.defaultMMKV().getString(VitalClient.Builder.Key.projectId, "").isEmpty()) {
            MMKV.defaultMMKV().putString(VitalClient.Builder.Key.projectId, "VivaLNK_SDKTest");
        }
        if (MMKV.defaultMMKV().getString(VitalClient.Builder.Key.subjectId, "").isEmpty()) {
            MMKV.defaultMMKV().putString(VitalClient.Builder.Key.subjectId, "testAndroid");
        }
        View view = LayoutInflater.from(this).inflate(R.layout.ecg_register_dialog, null);
        final EditText edtProjectId = (EditText) view.findViewById(R.id.edtProjectId);
        final EditText edtSubjectId = (EditText) view.findViewById(R.id.edtSubjectId);
        final Button btnOk = (Button) view.findViewById(R.id.yes_btn);
        // Initialize AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create(); // don't use setPositiveButton or setNegativeButton
        // Handle OK button
        btnOk.setOnClickListener(v -> {
            String projectId = edtProjectId.getText().toString();
            String subjectId = edtSubjectId.getText().toString();

            if (TextUtils.isEmpty(projectId)) {
                projectId = DeviceManager.projectId;
            }

            if (TextUtils.isEmpty(subjectId)) {
                subjectId = DeviceManager.subjectId;
            }
            MMKV.defaultMMKV().putString(VitalClient.Builder.Key.projectId, projectId);
            MMKV.defaultMMKV().putString(VitalClient.Builder.Key.subjectId, subjectId);

            dialog.dismiss();
        });
        dialog.show();
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window != null) {
            // 1. Remove default dialog padding background
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // 2. Set left & right margins by reducing width
            int marginInDp = 24; // margin on each side
            int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

            int marginInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, marginInDp, getResources().getDisplayMetrics());

            // Apply calculated width with margin, keep height wrap_content
            int dialogWidth = screenWidth - (2 * marginInPx);
            window.setLayout(dialogWidth, WindowManager.LayoutParams.WRAP_CONTENT);
        }

    }


}
