package com.optimum.Avica.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.optimum.Avica.R;

public class SplashActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent intent = new Intent(SplashActivity.this, SelectUserActivity.class);
                    startActivity(intent);
                    finish();

            }
        },3000);

    }
}