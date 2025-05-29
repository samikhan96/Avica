package com.optimum.Avica.UI.Patient;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.DoctorProfile.ProfileData;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.AppUtils;
import com.optimum.Avica.Utils.UserPrefs;

import de.hdodenhof.circleimageview.CircleImageView;

public class DocProfileActivity extends AppCompatActivity {

    private CircleImageView prpfile_img;
    TextView tvName, tvEmail, tvDOB, tvPhone, tvHeight, tvWeight, tvLanguage, tvSSN, tvSubscriberId, tvMeasurementSystem, tvTimeZone;
    ProfileData doctorProfile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_profile);
        user= UserPrefs.getGetUser();
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


        getDoctorProfile(user.id);
    }

    public void getDoctorProfile(String id){
        AppUtils.showProgressDialog(DocProfileActivity.this);

        AppServices.DoctorProfile(DocProfileActivity.class.getSimpleName(),id, new ServiceListener<ProfileData, String>() {
            @Override
            public void success(ProfileData success) {
                doctorProfile= success;
                AppUtils.dismisProgressDialog(DocProfileActivity.this);
                setValues();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(DocProfileActivity.this);

            }
        });
    }

    public void setValues(){
        // Set Text Values+
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(DocProfileActivity.this).load(doctorProfile.getUri()).apply(options).into(prpfile_img);

        tvName.setText(doctorProfile.getFirstName() + " " + doctorProfile.getLastName());
        tvEmail.setText(doctorProfile.getEmail());
        AppUtils.setFormattedDate(doctorProfile.getDob(),tvDOB);
        tvPhone.setText(doctorProfile.getPhoneNumber());
        tvHeight.setText(doctorProfile.getEmail());
        tvWeight.setText(doctorProfile.getDoctor().getSpecialities().get(0).getTitle());
        tvLanguage.setText(doctorProfile.getTitle());
        tvSSN.setText(doctorProfile.getDoctor().getEmployeeId());
        tvSubscriberId.setText(doctorProfile.getDoctor().getSpecialities().get(0).getInstituteName());
        tvMeasurementSystem.setText(doctorProfile.getDoctor().getNpiNumber());
        tvTimeZone.setText(doctorProfile.getLocation().getAddress());


    }
}
