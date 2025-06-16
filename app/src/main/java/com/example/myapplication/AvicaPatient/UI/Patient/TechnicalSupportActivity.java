package com.example.myapplication.AvicaPatient.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.Dialogs.RequestDialog;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class TechnicalSupportActivity extends AppCompatActivity {

    Button loginBtn;
    EditText et_email, et_text;
    String email, text;
    boolean forgot_password = false, not_signing_up = false, can_not_find_anything = false, showing_errors = false, others = false;

    CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical_support);
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        et_email = findViewById(R.id.et_email);
        et_text = findViewById(R.id.et_text);
        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        checkbox4 = findViewById(R.id.checkbox4);
        checkbox5 = findViewById(R.id.checkbox5);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        // Assign the same listener to all checkboxes
        View.OnClickListener checkboxClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) v;
                    boolean isChecked = checkBox.isChecked();

                    switch (v.getId()) {
                        case R.id.checkbox1:
                            if (isChecked) {
                                forgot_password = true;
                            }
                            break;
                        case R.id.checkbox2:
                            if (isChecked) {
                                not_signing_up = true;
                            }
                            break;
                        case R.id.checkbox3:
                            if (isChecked) {
                                can_not_find_anything = true;
                            }

                            break;
                        case R.id.checkbox4:
                            if (isChecked) {
                                showing_errors = true;
                            }

                            break;
                        case R.id.checkbox5:
                            if (isChecked) {
                                others = true;
                            } else {

                            }

                            break;
                    }
                }
            }
        };

        checkbox1.setOnClickListener(checkboxClickListener);
        checkbox2.setOnClickListener(checkboxClickListener);
        checkbox3.setOnClickListener(checkboxClickListener);
        checkbox4.setOnClickListener(checkboxClickListener);
        checkbox5.setOnClickListener(checkboxClickListener);
    }

    public boolean validate() {
        email = et_email.getText().toString();
        text = et_text.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(text)) {
            AppUtils.Toast("Please fill all fields");
            return false;
        }
        TechnicalSupport();
        return true;
    }

    public void TechnicalSupport() {

        AppUtils.showProgressDialog(TechnicalSupportActivity.this);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("forgot_password", forgot_password);
            jsonObject.put("not_signing_up", not_signing_up);
            jsonObject.put("can_not_find_anything", can_not_find_anything);
            jsonObject.put("showing_errors", showing_errors);
            jsonObject.put("others", others);
            jsonObject.put("email", email);
            jsonObject.put("textarea", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppServices.TechnicalSupport(TechnicalSupportActivity.class.getSimpleName(), jsonObject, new ServiceListener<String, String>() {
            @Override
            public void success(String success) {
                AppUtils.dismisProgressDialog(TechnicalSupportActivity.this);

                RequestDialog cdd = new RequestDialog(TechnicalSupportActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(TechnicalSupportActivity.this);

            }
        });
    }

}