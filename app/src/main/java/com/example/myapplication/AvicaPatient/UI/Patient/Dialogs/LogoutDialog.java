package com.example.myapplication.AvicaPatient.UI.Patient.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.LoginActivity;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

public class LogoutDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes_btn;
    public Button no_btn;

    public LogoutDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout_dialog);
        yes_btn = (Button) findViewById(R.id.yes_btn);
        no_btn = (Button) findViewById(R.id.no_btn);


        yes_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btn:
                UserPrefs.getInstance().clearDoctorUser();
                c.startActivity(new Intent(c, LoginActivity.class));
                c.finish();
                break;
            case R.id.no_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}


