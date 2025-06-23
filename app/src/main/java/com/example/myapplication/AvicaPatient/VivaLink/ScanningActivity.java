package com.example.myapplication.AvicaPatient.VivaLink;

import static com.vivalnk.sdk.demo.base.app.BaseActivity.navTo;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.CircularProgressView;
import com.example.myapplication.AvicaPatient.VivaLink.adapter.ScanListAdapter;
import com.google.android.material.navigation.NavigationView;
import com.vivalnk.sdk.VitalClient;
import com.vivalnk.sdk.ble.BluetoothScanListener;
import com.vivalnk.sdk.common.ble.scan.ScanOptions;
import com.vivalnk.sdk.common.eventbus.EventBus;
import com.vivalnk.sdk.common.eventbus.Subscribe;
import com.vivalnk.sdk.common.eventbus.ThreadMode;
import com.vivalnk.sdk.common.utils.EventBusHelper;
import com.vivalnk.sdk.common.utils.log.VitalLog;
import com.vivalnk.sdk.demo.base.app.Layout;
import com.vivalnk.sdk.demo.base.i18n.ErrorMessageHandler;
import com.vivalnk.sdk.demo.repository.device.ConnectEvent;
import com.vivalnk.sdk.demo.repository.device.DeviceManager;
import com.vivalnk.sdk.demo.repository.device.ScanEvent;
import com.vivalnk.sdk.model.Device;
import com.vivalnk.sdk.open.evnet.TimezoneSyncEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import com.example.myapplication.AvicaPatient.VivaLink.adapter.ScanListAdapter.StatusDevice;



public class ScanningActivity extends BaseDeviceActivity {


    private String tag="devicetag";

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ScanningActivity.class);
        return intent;
    }

    private LinkedHashSet<StatusDevice> deviceLinkedHashSet;
    private List<StatusDevice> deviceArrayList;
    private ScanListAdapter recycleAdapter;
    private Boolean mIsScanning = false;

    RecyclerView rvScanList;

    private BluetoothScanListener scanListener = new BluetoothScanListener() {
        @Override
        public void onStart() {
            runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onStart()));

            Iterator<StatusDevice> it = deviceLinkedHashSet.iterator();
            while (it.hasNext()) {
                StatusDevice statusDevice = it.next();
                if (!DeviceManager.getInstance().isConnected(statusDevice.device)) {
                    it.remove();
                }
            }

            updateList();

            mIsScanning = true;

        }

        @Override
        public void onDeviceFound(Device device) {
            runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onDeviceFound(device)));
        }

        @Override
        public void onStop() {
            runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onStop()));
        }

        @Override
        public void onError(int code, String msg) {
            runOnUiThread(() -> EventBus.getDefault().post(ScanEvent.onError(code, msg)));
            Toast.makeText(ScanningActivity.this.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    };

    private Comparator comparator = new Comparator<StatusDevice>() {
        @Override
        public int compare(StatusDevice o1, StatusDevice o2) {
            int rssi1 = o1.device.getRssi();
            int rssi2 = o2.device.getRssi();
            if (o1.connect) {
                rssi1 = Math.abs(rssi1);
            }

            if (o2.connect) {
                rssi2 = Math.abs(rssi2);
            }

            if (o1.connect && o2.connect) {
                return rssi1 - rssi2;
            } else {
                return rssi2 - rssi1;
            }
        }
    };

    private volatile boolean isFirstIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        try {
            if(DeviceManager.getInstance().allowConnectLastConnectedDevice){
                VitalClient.getInstance().connectLastDevice();
                isFirstIn = true;
            }else{
                isFirstIn = false;
            }
        } catch (Exception e) {
            //restart app
            startActivity(new Intent(this, WelcomeActivity.class));
            this.finish();
        }


        isFirstIn = true;
        EventBusHelper.getDefault().register(this);
    }

    /**
     * 开始扫描
     */
    private void startScan() {
        if (!checkBLE()) {
            return;
        }

        if (mIsScanning) {
            return;
        }

        ScanOptions options = new ScanOptions.Builder()
                .setTimeout(30 * 1000)
                .setEnableLog(true)
                .build();
        VitalClient.getInstance().startScan(options, scanListener);

    }

    private void updateList() {
        if (deviceArrayList == null) {
            deviceArrayList = new ArrayList<>();
        }
        deviceArrayList.clear();
        deviceArrayList.addAll(new ArrayList<>(deviceLinkedHashSet));
        Collections.sort(deviceArrayList, comparator);
        recycleAdapter.notifyDataSetChanged();
    }

    /**
     * 关闭扫描
     */

    @Override
    protected void onResume() {
        super.onResume();
        if (recycleAdapter != null) {
            recycleAdapter.notifyDataSetChanged();
        }
        VitalLog.e(tag+" onResume  isFirstIn= " + isFirstIn+ " mIsScanning="+mIsScanning);

        if (isFirstIn) {
            startScan();
        }
    }


    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onDestroy();
        EventBusHelper.getDefault().unregister(this);
    }

    @Override
    protected void onConnectChanged(ConnectEvent connectEvent) {
        super.onConnectChanged(connectEvent);
        VitalLog.e(tag+" onConnectChanged= " + connectEvent.event);

        if (ConnectEvent.ON_START.equalsIgnoreCase(connectEvent.event)) {

        } else if (ConnectEvent.ON_CONNECTED.equalsIgnoreCase(connectEvent.event)) {

        } else if (ConnectEvent.ON_DEVICE_READY.equalsIgnoreCase(connectEvent.event)) {
            updateContent(true, connectEvent.device);
            if (null != mTimezoneSwitchDialog && mTimezoneSwitchDialog.isShowing()) {
                return;
            }
            navToDeviceActivity(ScanningActivity.this, connectEvent.device);
        } else if (ConnectEvent.ON_DISCONNECTED.equalsIgnoreCase(connectEvent.event)) {
            updateContent(false, connectEvent.device);
            AppUtils.Toast(
                    ErrorMessageHandler.getInstance().getDisconnectedMeesage(connectEvent.device, connectEvent.isForce));
        } else if (ConnectEvent.ON_ERROR.equalsIgnoreCase(connectEvent.event)) {
            updateContent(false, connectEvent.device);
            AppUtils.Toast(ErrorMessageHandler.getInstance().getConnectErrorMeesage(connectEvent.device, connectEvent.code, connectEvent.msg));
        }
    }

    private void updateContent(boolean connect, Device device) {
        StatusDevice target = new StatusDevice(device, connect);
        //update target
        deviceLinkedHashSet.remove(target);
        deviceLinkedHashSet.add(target);

        updateList();

        recycleAdapter.updateConnectStatus(connect, device);
    }

    private void initView() {

        CircularProgressView progressView = findViewById(R.id.progressView);
        startScanningAnimation(progressView);

        // list view
        rvScanList=findViewById(R.id.rvList);
        rvScanList.setLayoutManager(new LinearLayoutManager(this));
        rvScanList.setHasFixedSize(true);

        deviceLinkedHashSet = new LinkedHashSet<>();

        deviceArrayList = new ArrayList<>();
        recycleAdapter = new ScanListAdapter(deviceArrayList,
                (itemView, position, device) -> {
                    if (checkBLE() == false) {
                        return;
                    }
                    if(deviceArrayList.get(position).connect != DeviceManager.getInstance().isConnected(device)){
                        return;
                    }
                    if (DeviceManager.getInstance().isConnected(device)) {
                        navToDeviceActivity(ScanningActivity.this, device);
                    } else {
                        DeviceManager.getInstance().connect(device);
                    }
                });
        rvScanList.setAdapter(recycleAdapter);

        //load pre connected list
        List<StatusDevice> devices = new ArrayList<>();
        try {
            for (Device device : VitalClient.getInstance().getConnectedDeviceList()) {
                StatusDevice tmp = new StatusDevice(device, true);
                devices.add(tmp);
            }
        } catch (Exception e) {
            startActivity(new Intent(this, ConfigActivity.class));
            this.finish();
        }
        deviceLinkedHashSet.addAll(devices);
        updateList();



    }

    public static void navToDeviceActivity(Context context, Device device) {
        Bundle extras = new Bundle();
        extras.putSerializable("device", device);
            navTo(context, extras, DeviceMenuActivity.class);

    }


    @UiThread
    private void onDeviceFound(Device device) {
        deviceLinkedHashSet.add(new StatusDevice(device));
        updateList();
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
        }





    @Override
    protected void onLocationTurnOff() {
        super.onLocationTurnOff();
    }

    @Override
    protected void onBluetoothTurnOff() {
        super.onBluetoothTurnOff();
    }

    private AlertDialog mTimezoneSwitchDialog;
    @com.vivalnk.sdk.common.eventbus.Subscribe(threadMode = com.vivalnk.sdk.common.eventbus.ThreadMode.MAIN)
    public void onEventECGTimezone(TimezoneSyncEvent event) {
        if (event.type == TimezoneSyncEvent.Type.SYNC_START) {
            if (event.type == TimezoneSyncEvent.Type.SYNC_START){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Tips")
                        .setMessage("Configuring to your time zone, you may need to wait for around 15 seconds for BLE disconnection and auto reconnection")
                        .setNegativeButton(R.string.cancel, null)
                        .setPositiveButton(R.string.ok, null);
                mTimezoneSwitchDialog = builder.show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScanEvent(ScanEvent event) {
        if (ScanEvent.ON_DEVICEFOUND.equalsIgnoreCase(event.event)) {
            onDeviceFound(event.device);
        } else if (ScanEvent.ON_STOP.equalsIgnoreCase(event.event) || ScanEvent.ON_ERROR.equalsIgnoreCase(event.event)) {
            mIsScanning = false;
        }
    }

    private void startScanningAnimation(CircularProgressView progressView) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 100f);
        animator.setDuration(2000); // 2 seconds per rotation
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);

        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            progressView.setProgress(animatedValue);
        });

        animator.start();
    }


}