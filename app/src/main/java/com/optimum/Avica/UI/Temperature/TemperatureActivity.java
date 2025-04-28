package com.optimum.Avica.UI.Temperature;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.optimum.Avica.R;
import com.optimum.Avica.UI.BloodGlucose.AutomaticActivity;
import com.optimum.Avica.UI.BloodGlucose.ManualActivity;
import com.optimum.Avica.UI.BloodGlucose.WatchTutorialActivity;
import com.optimum.Avica.UI.Spo2.Spo2Activity;

public class TemperatureActivity extends AppCompatActivity {


    ImageView item_1,item_2,item_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);


        item_1 = findViewById(R.id.item_1);
        item_2 = findViewById(R.id.item_2);
        item_3 = findViewById(R.id.item_3);


        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemperatureActivity.this, AutomaticActivity.class));
            }
        });

        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemperatureActivity.this, ManualActivity.class));
            }
        });

        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TemperatureActivity.this, WatchTutorialActivity.class));
            }
        });
    }
}