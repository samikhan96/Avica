package com.example.myapplication.AvicaPatient.UI.Patient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;

import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class VideoCall_Activity extends AppCompatActivity {

    private static final String TAG = "VideoCall_Activity";
    private static final int PERMISSION_REQ_ID = 22;

    private String appId = "0f23c5777fa24370bded56a7e133f88f"; // Agora App ID
    private String channelName;
    private String token;
    private String numericUid;

    private RtcEngine mRtcEngine;

    private boolean isCameraEnabled = true;
    private boolean isMicEnabled = true;
    private boolean isSpeakerEnabled = true;

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            Log.d(TAG, "onJoinChannelSuccess: Joined channel " + channel + " with UID " + uid);
            runOnUiThread(() ->
                    Toast.makeText(VideoCall_Activity.this, "Join channel success", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            Log.d(TAG, "onUserJoined: UID " + uid);
            runOnUiThread(() -> setupRemoteVideo(uid));
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            Log.d(TAG, "onUserOffline: UID " + uid + " Reason " + reason);
            runOnUiThread(() ->
                    Toast.makeText(VideoCall_Activity.this, "User offline: " + uid, Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onError(int err) {
            runOnUiThread(() -> handleAgoraError(err));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        String appointmentId = getIntent().getStringExtra("appointmentId");
        if (appointmentId != null) {
            channelName = appointmentId;
        } else {
            Toast.makeText(this, "Invalid appointment ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (checkPermissions()) {
            agoraToken();
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), PERMISSION_REQ_ID);
        }

        setupButtonListeners();
    }

    private void initializeAgoraEngine() {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getApplicationContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            mRtcEngine = RtcEngine.create(config);
        } catch (Exception e) {
            throw new RuntimeException("Agora SDK init failed: " + e.getMessage());
        }

        mRtcEngine.enableVideo();
        mRtcEngine.startPreview();
    }

    private void initializeAndJoinChannel() {
        if (mRtcEngine == null) return;

        // Set up local video
        FrameLayout localContainer = findViewById(R.id.local_video_view_container);
        SurfaceView localView = new SurfaceView(getBaseContext());
        localContainer.removeAllViews();
        localContainer.addView(localView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(localView, VideoCanvas.RENDER_MODE_FIT, 0));

        // Join channel
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;

        Log.d(TAG, "Joining channel: " + channelName);
        Log.d(TAG, "Token: " + token);
        mRtcEngine.joinChannel(token, channelName, Integer.parseInt(numericUid), options);
    }

    private void setupRemoteVideo(int uid) {
        FrameLayout remoteContainer = findViewById(R.id.remote_video_view_container);
        SurfaceView remoteView = new SurfaceView(getBaseContext());
        remoteView.setZOrderMediaOverlay(true);
        remoteContainer.removeAllViews();
        remoteContainer.addView(remoteView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(remoteView, VideoCanvas.RENDER_MODE_FIT, uid));
    }

    private void setupButtonListeners() {
        findViewById(R.id.card_camera).setOnClickListener(v -> {
            isCameraEnabled = !isCameraEnabled;
            mRtcEngine.muteLocalVideoStream(!isCameraEnabled);
            Toast.makeText(this, isCameraEnabled ? "Camera On" : "Camera Off", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.card_mic).setOnClickListener(v -> {
            isMicEnabled = !isMicEnabled;
            mRtcEngine.muteLocalAudioStream(!isMicEnabled);
            Toast.makeText(this, isMicEnabled ? "Mic On" : "Mic Off", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.card_speaker).setOnClickListener(v -> {
            isSpeakerEnabled = !isSpeakerEnabled;
            mRtcEngine.setEnableSpeakerphone(isSpeakerEnabled);
            Toast.makeText(this, isSpeakerEnabled ? "Speaker On" : "Speaker Off", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.card_end_call).setOnClickListener(v -> {
            mRtcEngine.leaveChannel();
            finish();
        });
    }

    private void agoraToken() {
        AppUtils.showProgressDialog(VideoCall_Activity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("channelName", channelName);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        AppServices.agoraToken(VideoCall_Activity.class.getSimpleName(), jsonObject, new ServiceListener<String, String>() {
            @Override
            public void success(String result) {

                if (result != null && result.contains("/")) {
                    String[] parts = result.split("/");
                    token = parts[0];
                    numericUid = parts[1]; // Now you have token and numericUid separately
                    Log.d(TAG, "Token: " + token);
                    Log.d(TAG, "numericUid: " + numericUid);
                    initializeAgoraEngine();
                    initializeAndJoinChannel();
                    AppUtils.dismisProgressDialog(VideoCall_Activity.this);
                }
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(VideoCall_Activity.this);
                Toast.makeText(VideoCall_Activity.this, "Token fetch failed: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleAgoraError(int errCode) {
        String message;
        switch (errCode) {
            case Constants.ERR_INVALID_APP_ID:
                message = "Invalid App ID. Check your App ID.";
                break;
            case Constants.ERR_INVALID_TOKEN:
                message = "Invalid token. Please request a new token.";
                break;
            case Constants.ERR_TOKEN_EXPIRED:
                message = "Token expired. Request a new token.";
                break;
            default:
                message = "Agora Error Code: " + errCode;
        }
        Log.e(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean checkPermissions() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private String[] getRequiredPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE
            };
        } else {
            return new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermissions()) {
            agoraToken();
        } else {
            Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRtcEngine != null) {
            mRtcEngine.stopPreview();
            mRtcEngine.leaveChannel();
            mRtcEngine = null;
        }
    }
}
