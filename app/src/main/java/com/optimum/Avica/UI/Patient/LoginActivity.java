package com.optimum.Avica.UI.Patient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.Dialogs.LoginDialog;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPassword;
    TextView techSupport;
    TextView privacyPolicy;
    TextView terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=findViewById(R.id.loginBtn);
        forgotPassword=findViewById(R.id.forgotPassword);
        techSupport=findViewById(R.id.techSupport);
        privacyPolicy=findViewById(R.id.privacyPolicy);
        terms=findViewById(R.id.terms);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginDialog cdd = new LoginDialog(LoginActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();



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
}