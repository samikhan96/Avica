package com.example.myapplication.AvicaPatient.UI.Patient.TeleMedicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AvicaPatient.Adapters.AdapterReport;
import com.example.myapplication.AvicaPatient.Adapters.AdapterTelemedicine;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentData;
import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentModel;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.ReportActivity;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import java.util.ArrayList;

public class TelemedActivity extends AppCompatActivity {

    AppointmentData appointmentData;
    TextView item_1,item_2,item_3,item_4;
    RecyclerView list1;
    ArrayList<AppointmentModel> appointmentModelArrayList = new ArrayList<>();
    AdapterTelemedicine adapterReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telemed);

        item_1=findViewById(R.id.item_1);
        item_2=findViewById(R.id.item_2);
        item_3=findViewById(R.id.item_3);
        item_4=findViewById(R.id.item_4);
        list1=findViewById(R.id.list1);

        ImageView back = findViewById(R.id.back);
        Button loginBtn = findViewById(R.id.loginBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelemedActivity.this, NewAppointmentActivity.class);
                startActivity(intent);
            }
        });
        getTelemedicineappointments();
    }

    public void getTelemedicineappointments() {
        AppUtils.showProgressDialog(TelemedActivity.this);

        AppServices.gettelemedappointments(TelemedActivity.class.getSimpleName(), new ServiceListener<AppointmentData, String>() {
            @Override
            public void success(AppointmentData success) {
                AppUtils.dismisProgressDialog(TelemedActivity.this);

                appointmentData = success;
                appointmentModelArrayList=appointmentData.Appointmentlist;
                updateData();
                setAdapter();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(TelemedActivity.this);

            }
        });
    }

    public void updateData(){

        item_1.setText(appointmentData.analytics.Active);
        item_2.setText(appointmentData.analytics.Complete);
        item_3.setText(appointmentData.analytics.Canceled);
        item_4.setText(appointmentData.analytics.Pending);
    }
    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list1.setLayoutManager(layoutManager);
        adapterReport = new AdapterTelemedicine(TelemedActivity.this, appointmentModelArrayList, this);
        list1.setAdapter(adapterReport);

    }


}