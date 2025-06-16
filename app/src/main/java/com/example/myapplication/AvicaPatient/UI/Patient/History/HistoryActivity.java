package com.example.myapplication.AvicaPatient.UI.Patient.History;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.AvicaPatient.Adapters.AdapterReport;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.ReportActivity;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    ImageView item1,item2,item3,item4,item5,item6;
    RecyclerView list1;
    ArrayList<Reports> reportsArrayList = new ArrayList<>();
    AdapterReport adapterReport;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        user = UserPrefs.getGetUser();

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        item1=findViewById(R.id.item_1);
        item2=findViewById(R.id.item_2);
        item3=findViewById(R.id.item_3);
        item4=findViewById(R.id.item_4);
        item5=findViewById(R.id.item_5);
        item6=findViewById(R.id.item_6);


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddFamilyHistory.class);
                startActivity(intent);
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddPresentComplain.class);
                startActivity(intent);
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddSurgicalProcedure.class);
                startActivity(intent);
            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddMedication.class);
                startActivity(intent);
            }
        });

        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddDiagnosis.class);
                startActivity(intent);
            }
        });

        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this, AddRecords.class);
                startActivity(intent);
            }
        });

        list1=findViewById(R.id.list1);
        getPHR();



    }
    public void getPHR(){
        AppUtils.showProgressDialog(HistoryActivity.this);

        AppServices.getReports(ReportActivity.class.getSimpleName(),user.id, new ServiceListener<ArrayList<Reports>, String>() {
            @Override
            public void success(ArrayList<Reports> success) {
                AppUtils.dismisProgressDialog(HistoryActivity.this);
                reportsArrayList = success;
                setAdapter();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(HistoryActivity.this);

            }
        });
    }
    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list1.setLayoutManager(layoutManager);
        adapterReport = new AdapterReport(HistoryActivity.this, reportsArrayList, this);
        list1.setAdapter(adapterReport);

    }

}