package com.optimum.Avica.UI.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.PatientProfile;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.AppUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements OnMapReadyCallback {

    private CircleImageView prpfile_img;
    TextView tvName, tvEmail, tvDOB, tvPhone, tvHeight, tvWeight, tvLanguage, tvSSN, tvSubscriberId, tvMeasurementSystem, tvTimeZone;
    PatientProfile patientProfile;
    private GoogleMap mMap;
    // Specify the latitude and longitude here
    private double latitude = 24.830918;
    private double longitude = 67.051161;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Profile Info
        prpfile_img = findViewById(R.id.prpfile_img);
        tvName = findViewById(R.id.tv_1);
        tvEmail = findViewById(R.id.tv_2);
        tvDOB = findViewById(R.id.tv_3);
        tvPhone = findViewById(R.id.tv_4);
        tvHeight = findViewById(R.id.tv_5);
        tvWeight = findViewById(R.id.tv_6);
        tvLanguage = findViewById(R.id.tv_7);
        tvSSN = findViewById(R.id.tv_8);
        tvSubscriberId = findViewById(R.id.tv_9);
        tvMeasurementSystem = findViewById(R.id.tv_10);
        tvTimeZone = findViewById(R.id.tv_11);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        getProfile();
    }

    public void getProfile(){
        AppUtils.showProgressDialog(ProfileActivity.this);

        AppServices.patientProfile(ProfileActivity.class.getSimpleName(), new ServiceListener<PatientProfile, String>() {
            @Override
            public void success(PatientProfile success) {
                patientProfile= success;
                AppUtils.dismisProgressDialog(ProfileActivity.this);
                setValues();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(ProfileActivity.this);

            }
        });
    }

    public void setValues(){

        // Set Text Values+
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(ProfileActivity.this).load(patientProfile.uri).apply(options).into(prpfile_img);

        tvName.setText(patientProfile.first_name + " " + patientProfile.last_name);
        tvEmail.setText(patientProfile.email);
        tvDOB.setText(patientProfile.dob);
        tvPhone.setText(patientProfile.phone_number);
        tvHeight.setText(patientProfile.patient.height);
        tvWeight.setText(patientProfile.patient.weight);
        tvLanguage.setText("English");
        tvSSN.setText("13127386184");
        tvSubscriberId.setText("1234-XYZ");
        tvMeasurementSystem.setText("Standard");
        tvTimeZone.setText("UTC+5");

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Create LatLng object for the given location
        LatLng location = new LatLng(latitude, longitude);

        // Add a marker at the specified location and move the camera
        mMap.addMarker(new MarkerOptions().position(location).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
    }

}