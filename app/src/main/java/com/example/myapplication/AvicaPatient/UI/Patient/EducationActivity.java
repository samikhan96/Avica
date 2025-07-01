package com.example.myapplication.AvicaPatient.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.AvicaPatient.Adapters.AdapterEducation;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Education;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import java.util.ArrayList;

public class EducationActivity extends AppCompatActivity {

    RecyclerView list1;
    ArrayList<Education> educationArrayList = new ArrayList<>();
    AdapterEducation adapterEducation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list1=findViewById(R.id.list1);


        getEducation();
    }

    public void getEducation(){
        AppUtils.showProgressDialog(EducationActivity.this);

        AppServices.getEducation(EducationActivity.class.getSimpleName(), new ServiceListener<ArrayList<Education>, String>() {
            @Override
            public void success(ArrayList<Education> success) {
                AppUtils.dismisProgressDialog(EducationActivity.this);
                educationArrayList = success;
                setAdapter();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(EducationActivity.this);

            }
        });
    }
    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list1.setLayoutManager(layoutManager);
        adapterEducation = new AdapterEducation(EducationActivity.this, educationArrayList, this);
        list1.setAdapter(adapterEducation);

    }

}