package com.example.myapplication.AvicaPatient.UI.Patient.TeleMedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.R;

public class TelemedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemed);

        ImageView back = findViewById(R.id.back);
        Button loginBtn = findViewById(R.id.loginBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelemedActivity.this, NewAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getTelemedicineappointments(){

        AppServices.getEducation();
    }
}