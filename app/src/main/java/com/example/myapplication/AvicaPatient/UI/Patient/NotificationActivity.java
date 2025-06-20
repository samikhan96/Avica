package com.example.myapplication.AvicaPatient.UI.Patient;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AvicaPatient.Adapters.AdapterPush;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.Notifications;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<Notifications> notificationsArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterPush adapterpush;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.listView);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getNotification();
    }

    public void getNotification() {
        AppUtils.showProgressDialog(NotificationActivity.this);

        AppServices.getNotificiation(NotificationActivity.class.getSimpleName(), new ServiceListener<ArrayList<Notifications>, String>() {
            @Override
            public void success(ArrayList<Notifications> success) {

                AppUtils.dismisProgressDialog(NotificationActivity.this);
                notificationsArrayList = success;
                setAdapter();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(NotificationActivity.this);

            }
        });
    }

    public void setAdapter() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapterpush = new AdapterPush(NotificationActivity.this, notificationsArrayList, this);
        recyclerView.setAdapter(adapterpush);

    }

}