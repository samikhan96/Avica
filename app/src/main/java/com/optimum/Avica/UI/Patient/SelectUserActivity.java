package com.optimum.Avica.UI.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


import com.optimum.Avica.HttpUtils.ConfigConstants;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.AppUtils;
import com.optimum.Avica.Utils.UserPrefs;

public class SelectUserActivity extends AppCompatActivity {

    ImageView item_1,item_2,item_3,item_4;
    public static String LoginType;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        user = UserPrefs.getGetUser();


        item_1 = findViewById(R.id.item_1);
        item_2 = findViewById(R.id.item_2);
        item_3 = findViewById(R.id.item_3);
        item_4 = findViewById(R.id.item_4);


        item_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginType="Doctor";
                if (user!=null) {
                    if (user.role.equalsIgnoreCase("doctor")) {
                        ConfigConstants.token = user.token;
                        startActivity(new Intent(SelectUserActivity.this, DashboardActivity.class));
                    }
                    else{
                        startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
                    }

                }
                else{
                    startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
                }
            }
        });

        item_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginType="Patient";
                if (user!=null) {
                 if (user.role.equalsIgnoreCase("patient")) {
                        ConfigConstants.token = user.token;
                        startActivity(new Intent(SelectUserActivity.this, DashboardActivity.class));
                    }
                 else{
                     startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
                 }

                }
                else{
                    startActivity(new Intent(SelectUserActivity.this, LoginActivity.class));
                }
            }
        });

        item_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.Toast("Coming Soon");
            }
        });
        item_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.Toast("Coming Soon");
            }
        });
    }
}