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

import com.example.myapplication.AvicaPatient.R;

public class VideoCall_Activity extends AppCompatActivity {

    private static final String TAG = "VideoCall_Activity";
    private String appId = "0f23c5777fa24370bded56a7e133f88f"; // Your Agora App ID
    private String channelName; // Channel name set from appointmentId
    private String token = null; // Token can be set here if needed

    private RtcEngine mRtcEngine;

    private boolean isCameraEnabled = true; // Track camera state
    private boolean isMicEnabled = true; // Track microphone state
    private boolean isSpeakerEnabled = true; // Track speaker state

    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            Log.d(TAG, "onJoinChannelSuccess: Joined channel " + channel + " with UID " + uid);
            runOnUiThread(() -> Toast.makeText(VideoCall_Activity.this, "Join channel success", Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onUserJoined(int uid, int elapsed) {
            Log.d(TAG, "onUserJoined: User joined with UID " + uid);
            runOnUiThread(() -> setupRemoteVideo(uid));
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            Log.d(TAG, "onUserOffline: User " + uid + " went offline, reason " + reason);
            runOnUiThread(() -> Toast.makeText(VideoCall_Activity.this, "User offline: " + uid, Toast.LENGTH_SHORT).show());
        }

        @Override
        public void onError(int err) {
            Log.e(TAG, "onError: Agora SDK Error: " + err);
        }
    };

    private static final int PERMISSION_REQ_ID = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        Log.d(TAG, "onCreate: Activity started");

        // Retrieve appointmentId from the Intent
        String appointmentId = getIntent().getStringExtra("appointmentId");
        if (appointmentId != null) {
            channelName = appointmentId;
            Log.d(TAG, "onCreate: Channel name set to " + channelName);
        } else {
            Log.e(TAG, "onCreate: No appointmentId provided");
        }

        // Check permissions and initialize Agora engine
        if (checkPermissions()) {
            Log.d(TAG, "onCreate: Permissions granted, initializing Agora engine");
            initializeAgoraEngine();
        } else {
            Log.d(TAG, "onCreate: Permissions not granted, requesting permissions");
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), PERMISSION_REQ_ID);
        }

        // Set up button listeners
        setupButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure permissions are granted and join the channel
        Log.d(TAG, "onResume: Checking permissions and joining channel");
        if (checkPermissions()) {
            initializeAndJoinChannel();
        } else {
            Log.e(TAG, "onResume: Permissions not granted, cannot join channel");
        }
    }

    private void initializeAgoraEngine() {
        try {
            Log.d(TAG, "initializeAgoraEngine: Initializing Agora SDK");
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            mRtcEngine = RtcEngine.create(config);
            Log.d(TAG, "initializeAgoraEngine: Agora SDK initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "initializeAgoraEngine: Agora SDK initialization failed", e);
            throw new RuntimeException("Agora SDK initialization failed: " + e.getMessage());
        }
        mRtcEngine.enableVideo();
        mRtcEngine.startPreview();
    }

    private void initializeAndJoinChannel() {
        if (mRtcEngine == null) {
            Log.e(TAG, "initializeAndJoinChannel: mRtcEngine is null");
            return;
        }

        Log.d(TAG, "initializeAndJoinChannel: Setting up local video");
        FrameLayout container = findViewById(R.id.local_video_view_container);
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        container.addView(surfaceView);
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0));

        ChannelMediaOptions options = new ChannelMediaOptions();
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;

        Log.d(TAG, "initializeAndJoinChannel: Joining channel " + channelName);
        mRtcEngine.joinChannel(token, channelName, 0, options);
    }

    private void setupRemoteVideo(int uid) {
        Log.d(TAG, "setupRemoteVideo: Setting up remote video for UID " + uid);
        FrameLayout container = findViewById(R.id.remote_video_view_container);
        SurfaceView surfaceView = new SurfaceView(getBaseContext());
        surfaceView.setZOrderMediaOverlay(true);
        container.addView(surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
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


    private boolean checkPermissions() {
        for (String permission : getRequiredPermissions()) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "checkPermissions: Permission " + permission + " not granted");
                return false;
            }
        }
        return true;
    }

    private void setupButtonListeners() {
        // Camera Toggle Button
        CardView cameraButton = findViewById(R.id.card_camera);
        cameraButton.setOnClickListener(v -> {
            isCameraEnabled = !isCameraEnabled;
            mRtcEngine.muteLocalVideoStream(!isCameraEnabled);
            Log.d(TAG, "setupButtonListeners: Camera " + (isCameraEnabled ? "enabled" : "disabled"));
            Toast.makeText(this, isCameraEnabled ? "Camera Enabled" : "Camera Disabled", Toast.LENGTH_SHORT).show();
        });

        // Microphone Toggle Button
        CardView micButton = findViewById(R.id.card_mic);
        micButton.setOnClickListener(v -> {
            isMicEnabled = !isMicEnabled;
            mRtcEngine.muteLocalAudioStream(!isMicEnabled);
            Log.d(TAG, "setupButtonListeners: Microphone " + (isMicEnabled ? "enabled" : "disabled"));
            Toast.makeText(this, isMicEnabled ? "Microphone Enabled" : "Microphone Disabled", Toast.LENGTH_SHORT).show();
        });

        // Speaker Toggle Button
        CardView speakerButton = findViewById(R.id.card_speaker);
        speakerButton.setOnClickListener(v -> {
            isSpeakerEnabled = !isSpeakerEnabled;
            mRtcEngine.setEnableSpeakerphone(isSpeakerEnabled);
            Log.d(TAG, "setupButtonListeners: Speaker " + (isSpeakerEnabled ? "enabled" : "disabled"));
            Toast.makeText(this, isSpeakerEnabled ? "Speaker Enabled" : "Speaker Disabled", Toast.LENGTH_SHORT).show();
        });

        // End Call Button
        CardView endCallButton = findViewById(R.id.card_end_call);
        endCallButton.setOnClickListener(v -> {
            Log.d(TAG, "setupButtonListeners: Ending call");
            mRtcEngine.leaveChannel();
            finish(); // Close the activity
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: Permissions result received");
        if (checkPermissions()) {
            initializeAgoraEngine();
            initializeAndJoinChannel();
        } else {
            Log.e(TAG, "onRequestPermissionsResult: Permissions not granted");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Cleaning up");
        if (mRtcEngine != null) {
            mRtcEngine.stopPreview();
            mRtcEngine.leaveChannel();
        }
    }
}

