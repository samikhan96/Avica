package com.optimum.Avica.UI.Patient.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.DashboardActivity;

public class Appointment_Create_Doalog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes_btn;

    public Appointment_Create_Doalog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.appointment_create_doalog);
        yes_btn = (Button) findViewById(R.id.yes_btn);


        yes_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btn:
                c.startActivity(new Intent(c, DashboardActivity.class));
                c.finish();
                break;

            default:
                break;
        }
        dismiss();
    }
}


