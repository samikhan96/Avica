package com.optimum.Avica.UI.Patient.TeleMedicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.LoginActivity;
import com.optimum.Avica.UI.Patient.PinActivity;

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
}