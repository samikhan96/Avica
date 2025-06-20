package com.example.myapplication.AvicaPatient.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.PatientProfile;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView prpfile_img;
    TextView tvName, tvEmail, tvDOB, tvPhone, tvHeight, tvWeight, tvLanguage, tvSSN,
            tvSubscriberId, tvMeasurementSystem, tvTimeZone,tv_12;
    PatientProfile patientProfile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = UserPrefs.getGetUser();
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
        tv_12 = findViewById(R.id.tv_12);


        getProfile(user.id);
    }

    public void getProfile(String id){
        AppUtils.showProgressDialog(ProfileActivity.this);

        AppServices.patientProfile(ProfileActivity.class.getSimpleName(),id, new ServiceListener<PatientProfile, String>() {
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
        AppUtils.setFormattedDate(patientProfile.dob,tvDOB);
        tvPhone.setText(patientProfile.phone_number);
        tvHeight.setText(patientProfile.patient.height);
        tvWeight.setText(patientProfile.patient.weight);
        tvLanguage.setText(patientProfile.language);
        tvSSN.setText(patientProfile.SSN);
        tvSubscriberId.setText(patientProfile.subscribeId);
        tvMeasurementSystem.setText(patientProfile.measurementSystem);
        tvTimeZone.setText(patientProfile.timeZone);
        tv_12.setText(patientProfile.patient.emr);

    }



}