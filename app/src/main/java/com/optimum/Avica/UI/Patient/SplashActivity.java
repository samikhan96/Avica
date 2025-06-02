package com.optimum.Avica.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.optimum.Avica.HttpUtils.ConfigConstants;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.UserPrefs;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user = UserPrefs.getGetUser();


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user!=null) {
                        ConfigConstants.token = user.token;
                        startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    

            }
        }, 3000);

    }
}