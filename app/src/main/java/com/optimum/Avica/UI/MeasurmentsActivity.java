package com.optimum.Avica.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.optimum.Avica.R;
import com.optimum.Avica.UI.BloodGlucose.BloodGlucoseActivity;
import com.optimum.Avica.UI.BloodPressure.BloodPressureActivity;
import com.optimum.Avica.UI.Ecg.EcgActivity;
import com.optimum.Avica.UI.Respiratory.RespiratoryActivity;
import com.optimum.Avica.UI.Spo2.Spo2Activity;
import com.optimum.Avica.UI.Temperature.TemperatureActivity;


public class MeasurmentsActivity extends AppCompatActivity {

    LinearLayout l1;
    ImageView item_1,item_2,item_3,item_4,item_5,item_6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurments);
        l1 = findViewById(R.id.l1);
        item_1 = findViewById(R.id.item_1);
        item_2 = findViewById(R.id.item_2);
        item_3 = findViewById(R.id.item_3);
        item_4 = findViewById(R.id.item_4);
        item_5 = findViewById(R.id.item_5);
        item_6 = findViewById(R.id.item_6);


        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, DashboardActivity.class));
                finish();
            }
        });

        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, BloodGlucoseActivity.class));
            }
        });

        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, BloodPressureActivity.class));
            }
        });

        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, EcgActivity.class));
            }
        });

        item_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, TemperatureActivity.class));
            }
        });

        item_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, Spo2Activity.class));
            }
        });

        item_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeasurmentsActivity.this, RespiratoryActivity.class));
            }
        });

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}