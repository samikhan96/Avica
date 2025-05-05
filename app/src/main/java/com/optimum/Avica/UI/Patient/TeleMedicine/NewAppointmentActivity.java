package com.optimum.Avica.UI.Patient.TeleMedicine;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.Dialogs.Appointment_Create_Doalog;
import com.optimum.Avica.UI.Patient.Dialogs.LoginDialog;
import com.optimum.Avica.UI.Patient.LoginActivity;

public class NewAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
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
                Appointment_Create_Doalog cdd = new Appointment_Create_Doalog(NewAppointmentActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });

    }
}