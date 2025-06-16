package com.example.myapplication.AvicaPatient.UI.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class PinActivity extends AppCompatActivity {

    Button loginBtn;
    PinView pinView;
    EditText password;
    String pass,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        loginBtn=findViewById(R.id.loginBtn);
        pinView=findViewById(R.id.pinview);
        password=findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();

            }
        });
    }

    public boolean validate() {
        pin = pinView.getText().toString();
        pass = password.getText().toString();
        if (TextUtils.isEmpty(pin) || TextUtils.isEmpty(pass)) {
            AppUtils.Toast("Please fill all fields");
            return false;
        }
        ResetPassword();
        return true;
    }

    public void ResetPassword(){
        AppUtils.showProgressDialog(PinActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email",ForgetPasswordActivity.emailsaved);
            jsonObject.put("otp", pin);
            jsonObject.put("password", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppServices.ResetPass(PinActivity.class.getSimpleName(), jsonObject, new ServiceListener<String, String>() {
            @Override
            public void success(String success) {
                AppUtils.dismisProgressDialog(PinActivity.this);

                Intent intent = new Intent(PinActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(PinActivity.this);

            }
        });

    }
}
