package com.optimum.Avica.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.optimum.Avica.Adapters.AdapterEducation;
import com.optimum.Avica.Adapters.AdapterPush;
import com.optimum.Avica.Adapters.CustomAdapter;
import com.optimum.Avica.HttpUtils.AppServices;
import com.optimum.Avica.Listener.ServiceListener;
import com.optimum.Avica.Models.Education;
import com.optimum.Avica.Models.Item;
import com.optimum.Avica.Models.Notifications;
import com.optimum.Avica.R;
import com.optimum.Avica.Utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    RecyclerView list1,list2;
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
        list2=findViewById(R.id.list2);


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
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        list1.setLayoutManager(layoutManager);
        adapterEducation = new AdapterEducation(EducationActivity.this, educationArrayList, this);
        list1.setAdapter(adapterEducation);

    }

}