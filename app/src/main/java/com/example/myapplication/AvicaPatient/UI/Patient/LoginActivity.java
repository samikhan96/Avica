package com.example.myapplication.AvicaPatient.UI.Patient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.HttpUtils.ConfigConstants;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.Dialogs.LoginDialog;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPassword;
    TextView techSupport;
    TextView privacyPolicy;
    TextView terms;
    EditText et_email,password;
    String email, pass;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=findViewById(R.id.loginBtn);
        forgotPassword=findViewById(R.id.forgotPassword);
        techSupport=findViewById(R.id.techSupport);
        privacyPolicy=findViewById(R.id.privacyPolicy);
        terms=findViewById(R.id.terms);
        et_email=findViewById(R.id.et_email);
        password=findViewById(R.id.password);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    token = task.getResult();
                    Log.d("FCM", "FCM Token: " + token);
                });


//        PATIENTLOGIN
        et_email.setText("haris@avica.com");
        password.setText("Patient@123");


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);

            }
        });
        techSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, TechnicalSupportActivity.class);
                startActivity(intent);

            }
        });
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, PrivacyPolicyActivity.class);
                startActivity(intent);

            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, TermsAndConditionActivity.class);
                startActivity(intent);

            }
        });
    }
    public boolean validate() {
        email = et_email.getText().toString();
        pass = password.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            AppUtils.Toast("Please fill all fields");
            return false;
        }
        UserPrefs.getInstance().clearDoctorUser();
        Login();
        return true;
    }
    public void Login(){
        AppUtils.showProgressDialog(LoginActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("password", pass);
            jsonObject.put("device_token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppServices.Login(LoginActivity.class.getSimpleName(), jsonObject, new ServiceListener<User, String>() {
            @Override
            public void success(User success) {
                ConfigConstants.token = success.token;
                AppUtils.dismisProgressDialog(LoginActivity.this);
                LoginDialog cdd = new LoginDialog(LoginActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(LoginActivity.this);

            }
        });
    }
}