package com.example.myapplication.AvicaPatient.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.AvicaPatient.Adapters.AdapterMedication;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Medication;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

import java.util.ArrayList;

public class MedicationActivity extends AppCompatActivity {


    RecyclerView list1;
    ArrayList<Medication> medicationArrayList = new ArrayList<>();
    AdapterMedication adapterMedication;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
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
        AppUtils.showProgressDialog(MedicationActivity.this);

        AppServices.getMedication(MedicationActivity.class.getSimpleName(), user.id,new ServiceListener<ArrayList<Medication>, String>() {
            @Override
            public void success(ArrayList<Medication> success) {
                AppUtils.dismisProgressDialog(MedicationActivity.this);
                medicationArrayList = success;
                setAdapter();

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(MedicationActivity.this);

            }
        });
    }
    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list1.setLayoutManager(layoutManager);
        adapterMedication = new AdapterMedication(MedicationActivity.this, medicationArrayList, this);
        list1.setAdapter(adapterMedication);

    }


}