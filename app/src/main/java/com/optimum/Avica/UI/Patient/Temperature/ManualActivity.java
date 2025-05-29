package com.optimum.Avica.UI.Patient.Temperature;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.Dialogs.Measurement_TakenDialog;
import com.optimum.Avica.Utils.AppUtils;
import com.optimum.Avica.Utils.UserPrefs;

import org.json.JSONException;
import org.json.JSONObject;

public class ManualActivity extends AppCompatActivity {

    Button sendBtn;
    EditText et_email;
    String email;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_manual);
        user= UserPrefs.getGetUser();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        sendBtn=findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();


            }
        });
    }
    public boolean validate() {
        email = et_email.getText().toString();
        if (TextUtils.isEmpty(email) ) {
            AppUtils.Toast("Please fill all fields");
            return false;
        }
        AddBG();
        return true;
    }

    public void AddBG(){
        AppUtils.showProgressDialog(ManualActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", "Measurement testing");
            jsonObject.put("type", "TEMPERATURE");
            jsonObject.put("mode", "METRIC");
            jsonObject.put("unit", "FAHRENHEIT");
            jsonObject.put("reading_source", "MANUAL");
            jsonObject.put("value", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppServices.AddMeasurment(ManualActivity.class.getSimpleName(),user.id, jsonObject, new ServiceListener<String, String>() {
            @Override
            public void success(String success) {
                AppUtils.dismisProgressDialog(ManualActivity.this);
                Measurement_TakenDialog cdd = new Measurement_TakenDialog(ManualActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(ManualActivity.this);

            }
        });
    }

}