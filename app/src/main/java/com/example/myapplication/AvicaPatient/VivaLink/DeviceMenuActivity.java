package com.example.myapplication.AvicaPatient.VivaLink;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.AvicaPatient.R;
import com.vivalnk.sdk.BuildConfig;
import com.vivalnk.sdk.Callback;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.demo.base.app.ConnectedActivity;
import com.vivalnk.sdk.demo.base.app.Layout;
import com.vivalnk.sdk.demo.base.widget.LogListDialogView;
import com.vivalnk.sdk.demo.repository.device.DeviceManager;
import com.vivalnk.sdk.device.vv330.DataStreamMode;
import com.vivalnk.sdk.device.vv330.VV330Manager;
import com.vivalnk.sdk.model.SampleData;
import com.vivalnk.sdk.model.common.DataType;
import com.vivalnk.sdk.vital.ete.ETEResult;

import java.util.Map;


public class DeviceMenuActivity extends ConnectedActivity {

    Button btnSwitchDataMode;
    Button btnDisconnect;
    Button btnGraphics;

    private LogListDialogView mDataLogView;
    private LogListDialogView mOperationLogView;


    String ENGINEER_CLASS = "com.vivalnk.sdk.engineer.ui.EngineerActivity";

    @Subscribe
    public void onDataUpdate(SampleData data) {
        if (!data.getDeviceID().equals(mDevice.getId())) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mDataLogView == null) {
                    return;
                }
                mDataLogView.updateLog(data.toSimpleString());

                if (data.getData(DataType.DataKey.EEAlgo) != null) {
                    ETEResult eteResult = data.getData(DataType.DataKey.EEAlgo);
                    onETEResultUpdated(eteResult);
                }

            }
        });
    }

    private void onETEResultUpdated(ETEResult result) {
        StringBuilder eteResult = new StringBuilder("FB Algo. result :\n");
        eteResult.append(" TimeStamp=").append(result.dataTimeStamp);
        eteResult.append(", HR=").append(result.ETEcorrectedHr);
        eteResult.append(", artifactPercent=").append(result.ETEartifactPercent);
        eteResult.append(", minHR=").append(result.ETEminimalHr);
        eteResult.append(", maxHR=").append(result.ETEmaximalHr);
        eteResult.append(", EPOC=").append(result.ETEepoc);
        eteResult.append(", TLPeak=").append(result.ETEtrainingLoadPeak);
        eteResult.append(", TE=").append(result.ETEtrainingEffect);
        eteResult.append(", kcal=").append(result.ETEenergyExpenditure);
        eteResult.append(", kcalC=").append(result.ETEenergyExpenditureCumulative);
        eteResult.append(", maxMET=").append(result.ETEmaximalMET);
        eteResult.append(", METmins=").append(result.ETEmaximalMETminutes);
        eteResult.append(", METminPercentage=").append(result.ETEmaximalMETpercentage);
        eteResult.append(", RelaxStressIntensity=").append(result.ETErelaxStressIntensity);
        eteResult.append(", meanMAD=").append(result.ETEmeanMAD);
        eteResult.append(", state=").append(result.ETEcurrentState);
        eteResult.append(", StressBalance=").append(result.ETEstressBalance);
        eteResult.append(", resp=").append(result.ETErespirationRate);
        eteResult.append(", activityScore=").append(result.ETEactivityScore);
        eteResult.append(", sleepQuality=").append(result.ETEsleepQualityIndex);
        eteResult.append(", maxSleepQuality=").append(result.ETEmaxSleepQualityIndex);
        eteResult.append(", sleepStart=").append(result.ETEsleepStart);
        eteResult.append(", sleepEnd=").append(result.ETEsleepEnd);
        eteResult.append(", sleepEndCandidate=").append(result.ETEsleepEndCandidate);
        mDataLogView.updateLog(eteResult.toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
    }

    private void initView() {

        btnSwitchDataMode = findViewById(R.id.btnSwitchDataMode);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        btnGraphics = findViewById(R.id.btnGraphics);
        btnSwitchDataMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSwitchDataMode(view);
            }
        });
        btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog("Disconnecting...");
                DeviceManager.getInstance().disconnect(mDevice);

            }
        });
        btnGraphics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToConnectedActivity(mDevice, CustomEcgViewActivity.class);

            }
        });

        mDataLogView = new LogListDialogView();
        mOperationLogView = new LogListDialogView();

        mDataLogView.create(this);
        mOperationLogView.create(this);

    }


    @Override
    protected Layout getLayout() {
        return Layout.createLayoutByID(R.layout.activity_device_detail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//    取消常亮
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    public void clickSwitchDataMode(View view) {

        Callback callback = new Callback() {
            @Override
            public void onStart() {
                showProgressDialog("start switch Mode");
            }

            @Override
            public void onComplete(Map<String, Object> data) {
                dismissProgressDialog();
                showToast("switch mode successful");
                DataStreamMode mode = DeviceManager.getInstance().getVV330Manager(mDevice).getDataStreamMode();
                DeviceManager.putDataStreamMode(mDevice, mode);
            }

            @Override
            public void onError(int code, String msg) {
                dismissProgressDialog();
                showToast("switch mode error: code = " + code + ", msg = " + msg);
            }
        };
        if (BuildConfig.sdkChannel.equals("sdk01")) {
            showSwitchDataStreamModeDialog(callback);
        } else {
            showSwitchDataStreamMode01ADialog(callback);
        }


    }

    private void showSwitchDataStreamMode01ADialog(Callback callback) {
        VV330Manager vv330Manager = DeviceManager.getInstance().getVV330Manager(mDevice);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Switch Data Stream Mode")
                .setItems(R.array.stream_mode_01a, (dialog, which) -> {
                    if (which == 0) {
                        vv330Manager.switchToLiveMode(mDevice, callback);
                    } else if (which == 1) {
                        vv330Manager.switchToFullDualMode(mDevice, callback);
                    }
                });
        builder.create().show();
    }

    private void showSwitchDataStreamModeDialog(Callback callback) {
        VV330Manager vv330Manager = DeviceManager.getInstance().getVV330Manager(mDevice);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Switch Data Stream Mode")
                .setItems(R.array.stream_mode, (dialog, which) -> {
                    switch (which) {
                        case 0: //NoneMode
                            vv330Manager.switchToNoneMode(mDevice, callback);
                            break;
                        case 1: //DualMode
                            vv330Manager.switchToDualMode(mDevice, callback);
                            break;
                        case 2: //LiveMode
                            vv330Manager.switchToLiveMode(mDevice, callback);
                            break;
                        case 3: //FullDualMode
                            vv330Manager.switchToFullDualMode(mDevice, callback);
                            break;
                        case 4: //RTSMode
                            vv330Manager.switchToRTSMode(mDevice, callback);
                            break;
                    }
                });
        builder.create().show();
    }
}