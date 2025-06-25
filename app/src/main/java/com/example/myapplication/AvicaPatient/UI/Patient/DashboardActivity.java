package com.example.myapplication.AvicaPatient.UI.Patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.DashboardData;
import com.example.myapplication.AvicaPatient.Models.Dashboard_BG;
import com.example.myapplication.AvicaPatient.Models.Dashboard_BP;
import com.example.myapplication.AvicaPatient.Models.Dashboard_ECG;
import com.example.myapplication.AvicaPatient.Models.Dashboard_Spo2;
import com.example.myapplication.AvicaPatient.Models.Dashboard_Temp;
import com.example.myapplication.AvicaPatient.Models.User;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.History.HistoryActivity;
import com.example.myapplication.AvicaPatient.UI.Patient.TeleMedicine.TelemedActivity;
import com.example.myapplication.AvicaPatient.UI.Patient.Dialogs.LogoutDialog;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;
import com.example.myapplication.AvicaPatient.Utils.DonutChartView;
import com.example.myapplication.AvicaPatient.Utils.UserPrefs;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    ImageView drawerimage, noti, navFooter1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView drawer_img, profile_img;
    TextView name, drawer_name, specs, drawer_specs,
            bg_totalReading, bg_timeStamp, bg_tv_1, bg_tv_2, bg_tv_3,
            bp_totalReading, bp_timeStamp, bp_tv_1, bp_tv_2, bp_tv_3,
            Temp_totalReading, Temp_timeStamp, Temp_tv_1, Temp_tv_2, Temp_tv_3,
            spo2_totalReading, spo2_timeStamp, spo2_tv_1, spo2_tv_2, spo2_tv_3,
            ecg_totalReading, ecg_timeStamp, ecg_tv_1, ecg_tv_2, ecg_tv_3;
    LinearLayout l2,  l3;
    User user;
    DonutChartView bg_donutChart, bp_donutChart, Temp_donutChart, spo2_donutChart, ecg_donutChart;
    Dashboard_BG dashboardBg;
    Dashboard_BP dashboardBp;
    Dashboard_Temp dashboardTemp;
    Dashboard_Spo2 dashboardspo2;
    Dashboard_ECG dashboardECG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user = UserPrefs.getGetUser();

        drawerimage = findViewById(R.id.drawerimage);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navview);
        View header = navigationView.getHeaderView(0);
        drawer_img = header.findViewById(R.id.drawer_img);
        drawer_name = header.findViewById(R.id.drawer_name);
        drawer_specs = header.findViewById(R.id.drawer_specs);
        profile_img = findViewById(R.id.profile_img);
        name = findViewById(R.id.name);
        specs = findViewById(R.id.specs);
        navFooter1 = findViewById(R.id.footer_item_1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        noti = findViewById(R.id.noti);
        bg_donutChart = findViewById(R.id.bg_donutChart);
        bg_totalReading = findViewById(R.id.bg_totalReading);
        bg_timeStamp = findViewById(R.id.bg_timeStamp);
        bg_tv_1 = findViewById(R.id.bg_tv_1);
        bg_tv_2 = findViewById(R.id.bg_tv_2);
        bg_tv_3 = findViewById(R.id.bg_tv_3);

        bp_donutChart = findViewById(R.id.bp_donutChart);
        bp_totalReading = findViewById(R.id.bp_totalReading);
        bp_timeStamp = findViewById(R.id.bp_timeStamp);
        bp_tv_1 = findViewById(R.id.bp_tv_1);
        bp_tv_2 = findViewById(R.id.bp_tv_2);
        bp_tv_3 = findViewById(R.id.bp_tv_3);

        Temp_donutChart = findViewById(R.id.Temp_donutChart);
        Temp_totalReading = findViewById(R.id.Temp_totalReading);
        Temp_timeStamp = findViewById(R.id.Temp_timeStamp);
        Temp_tv_1 = findViewById(R.id.Temp_tv_1);
        Temp_tv_2 = findViewById(R.id.Temp_tv_2);
        Temp_tv_3 = findViewById(R.id.Temp_tv_3);

        spo2_donutChart = findViewById(R.id.spo2_donutChart);
        spo2_totalReading = findViewById(R.id.spo2_totalReading);
        spo2_timeStamp = findViewById(R.id.spo2_timeStamp);
        spo2_tv_1 = findViewById(R.id.spo2_tv_1);
        spo2_tv_2 = findViewById(R.id.spo2_tv_2);
        spo2_tv_3 = findViewById(R.id.spo2_tv_3);

        ecg_donutChart = findViewById(R.id.ecg_donutChart);
        ecg_totalReading = findViewById(R.id.ecg_totalReading);
        ecg_timeStamp = findViewById(R.id.ecg_timeStamp);
        ecg_tv_1 = findViewById(R.id.ecg_tv_1);
        ecg_tv_2 = findViewById(R.id.ecg_tv_2);
        ecg_tv_3 = findViewById(R.id.ecg_tv_3);

        name.setText(user.first_name + " " + user.last_name);
        drawer_name.setText(user.first_name + " " + user.last_name);
        specs.setText(user.username);
        drawer_specs.setText(user.username);

        // Set Text Values+
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(DashboardActivity.this).load(user.uri).apply(options).into(drawer_img);
        Glide.with(DashboardActivity.this).load(user.uri).apply(options).into(profile_img);

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, MeasurmentsActivity.class));
                finish();
            }
        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ChatActivity.class));
                finish();
            }
        });

        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, NotificationActivity.class));
            }
        });

        navFooter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                LogoutDialog cdd = new LogoutDialog(DashboardActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
        drawerimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.item1:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.item2:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);

                        intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item3:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, TelemedActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item4:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, EducationActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item5:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, MedicationActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item6:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, HistoryActivity.class);
                        startActivity(intent);
                        break;

                }

                return true;
            }
        });
        getPatientDashboard(user.id);

    }

    private void setupBgPieChart(int high, int normal, int low) {
        int total = high + normal + low;

        bg_totalReading.setText("" + total);
        // Example values
        int[] values = {high, normal, low};
        int[] colors = {
                0xFF2CC97D,
                0xFFF7B500,
                0xFFEF5DA8
        };
        bg_donutChart.setValues(values);
        bg_donutChart.setColors(colors);

    }

    private void setupBpPieChart(int high, int normal, int low) {
        int total = high + normal + low;

        bp_totalReading.setText("" + total);
        // Example values
        int[] values = {high, normal, low};
        int[] colors = {
                0xFF2CC97D,
                0xFFF7B500,
                0xFFEF5DA8
        };
        bp_donutChart.setValues(values);
        bp_donutChart.setColors(colors);

    }

    private void setupTempPieChart(int high, int normal, int low) {
        int total = high + normal + low;

        Temp_totalReading.setText("" + total);
        // Example values
        int[] values = {high, normal, low};
        int[] colors = {
                0xFF2CC97D,
                0xFFF7B500,
                0xFFEF5DA8
        };
        Temp_donutChart.setValues(values);
        Temp_donutChart.setColors(colors);

    }

    private void setupspo2PieChart(int high, int normal, int low) {
        int total = high + normal + low;

        spo2_totalReading.setText("" + total);
        // Example values
        int[] values = {high, normal, low};
        int[] colors = {
                0xFF2CC97D,
                0xFFF7B500,
                0xFFEF5DA8
        };
        spo2_donutChart.setValues(values);
        spo2_donutChart.setColors(colors);

    }

    private void setupecgPieChart(int high, int normal, int low) {
        int total = high + normal + low;

        ecg_totalReading.setText("" + total);
        // Example values
        int[] values = {high, normal, low};
        int[] colors = {
                0xFF2CC97D,
                0xFFF7B500,
                0xFFEF5DA8
        };
        ecg_donutChart.setValues(values);
        ecg_donutChart.setColors(colors);

    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void getPatientDashboard(String id) {
        AppUtils.showProgressDialog(DashboardActivity.this);

        AppServices.Dashboard(DashboardActivity.class.getSimpleName(), id, new ServiceListener<DashboardData, String>() {
            @Override
            public void success(DashboardData success) {
                AppUtils.dismisProgressDialog(DashboardActivity.this);
                dashboardBg = success.getBloodglucose();
                dashboardBp = success.getBloodpressure();
                dashboardTemp = success.getTemperature();
                dashboardspo2 = success.getSpo2();
                dashboardECG = success.getEcg();
                setBGdata();
                setBPdata();
                setTempdata();
                setspo2data();
                setecgdata();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(DashboardActivity.this);

            }

        });
    }


    public void setBGdata() {
        bg_tv_1.setText("• High: 235 mmdb " + "(" + dashboardBg.high + ")");
        bg_tv_2.setText("• Normal: 120 mmdb " + "(" + dashboardBg.normal + ")");
        bg_tv_3.setText("• Low: 80 mmdb " + "(" + dashboardBg.low + ")");
        bg_timeStamp.setText("6 pm 12-06-20");
        setupBgPieChart(dashboardBg.high, dashboardBg.normal, dashboardBg.low);
    }

    public void setBPdata() {
        bp_tv_1.setText("• High: 235 mmdb " + "(" + dashboardBp.high + ")");
        bp_tv_2.setText("• Normal: 120 mmdb " + "(" + dashboardBp.normal + ")");
        bp_tv_3.setText("• Low: 80 mmdb " + "(" + dashboardBp.low + ")");
        bp_timeStamp.setText("6 pm 12-06-20");
        setupBpPieChart(dashboardBp.high, dashboardBp.normal, dashboardBp.low);
    }

    public void setTempdata() {
        Temp_tv_1.setText("• High: 235 mmdb " + "(" + dashboardTemp.high + ")");
        Temp_tv_2.setText("• Normal: 120 mmdb " + "(" + dashboardTemp.normal + ")");
        Temp_tv_3.setText("• Low: 80 mmdb " + "(" + dashboardTemp.low + ")");
        Temp_timeStamp.setText("6 pm 12-06-20");
        setupTempPieChart(dashboardTemp.high, dashboardTemp.normal, dashboardTemp.low);
    }

    public void setspo2data() {
        spo2_tv_1.setText("• High: 235 mmdb " + "(" + dashboardspo2.high + ")");
        spo2_tv_2.setText("• Normal: 120 mmdb " + "(" + dashboardspo2.normal + ")");
        spo2_tv_3.setText("• Low: 80 mmdb " + "(" + dashboardspo2.low + ")");
        spo2_timeStamp.setText("6 pm 12-06-20");
        setupspo2PieChart(dashboardspo2.high, dashboardspo2.normal, dashboardspo2.low);
    }

    public void setecgdata() {
        ecg_tv_1.setText("• High: 235 mmdb " + "(" + dashboardECG.high + ")");
        ecg_tv_2.setText("• Normal: 120 mmdb " + "(" + dashboardECG.normal + ")");
        ecg_tv_3.setText("• Low: 80 mmdb " + "(" + dashboardECG.low + ")");
        ecg_timeStamp.setText("6 pm 12-06-20");
        setupecgPieChart(dashboardECG.high, dashboardECG.normal, dashboardECG.low);
    }

}