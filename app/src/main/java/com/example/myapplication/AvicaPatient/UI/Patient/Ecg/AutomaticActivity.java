package com.example.myapplication.AvicaPatient.UI.Patient.Ecg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.VivaLink.WelcomeActivity;

public class AutomaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_automatic);

        Button sendBtn = findViewById(R.id.sendBtn);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AutomaticActivity.this, WelcomeActivity.class));

            }
        });
    }
}