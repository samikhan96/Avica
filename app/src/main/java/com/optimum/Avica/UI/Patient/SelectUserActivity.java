package com.optimum.Avica.UI.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.optimum.Avica.R;

public class SelectUserActivity extends AppCompatActivity {

    ImageView item_1,item_2,item_3,item_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        item_1 = findViewById(R.id.item_1);
        item_2 = findViewById(R.id.item_2);
        item_3 = findViewById(R.id.item_3);
        item_4 = findViewById(R.id.item_4);


        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
            }
        });

        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
            }
        });

        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
            }
        });
        item_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
            }
        });
    }
}