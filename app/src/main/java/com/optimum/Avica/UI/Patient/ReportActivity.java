package com.optimum.Avica.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.optimum.Avica.Adapters.AdapterMedication;
import com.optimum.Avica.Adapters.AdapterReport;
import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.Medication;
import com.optimum.Avica.Models.Reports;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.AppUtils;
import com.optimum.Avica.Utils.UserPrefs;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    RecyclerView list1;
    ArrayList<Reports> reportsArrayList = new ArrayList<>();
    AdapterReport adapterReport;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        user = UserPrefs.getGetUser();
        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list1=findViewById(R.id.list1);
        getMedication();


    }

    public void getMedication(){
        AppUtils.showProgressDialog(ReportActivity.this);

        AppServices.getReports(ReportActivity.class.getSimpleName(),user.id, new ServiceListener<ArrayList<Reports>, String>() {
            @Override
            public void success(ArrayList<Reports> success) {
                AppUtils.dismisProgressDialog(ReportActivity.this);
                reportsArrayList = success;
                setAdapter();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(ReportActivity.this);

            }
        });
    }
    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list1.setLayoutManager(layoutManager);
        adapterReport = new AdapterReport(ReportActivity.this, reportsArrayList, this);
        list1.setAdapter(adapterReport);

    }


}