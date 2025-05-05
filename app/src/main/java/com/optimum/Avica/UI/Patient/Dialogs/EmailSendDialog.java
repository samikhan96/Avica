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
import com.optimum.Avica.UI.Patient.PinActivity;

public class EmailSendDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes_btn;

    public EmailSendDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.email_send_dialog);
        yes_btn = (Button) findViewById(R.id.yes_btn);


        yes_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes_btn:
                c.startActivity(new Intent(c, PinActivity.class));
                c.finish();
                break;

            default:
                break;
        }
        dismiss();
    }
}


