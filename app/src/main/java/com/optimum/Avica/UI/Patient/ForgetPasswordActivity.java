package com.optimum.Avica.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.Dialogs.EmailSendDialog;
import com.optimum.Avica.UI.Patient.Dialogs.LoginDialog;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);



        loginBtn=findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailSendDialog cdd = new EmailSendDialog(ForgetPasswordActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });
    }
}