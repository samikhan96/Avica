package com.optimum.Avica.UI.Patient;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.History.HistoryActivity;
import com.optimum.Avica.UI.Patient.TeleMedicine.TelemedActivity;
import com.optimum.Avica.UI.Patient.Dialogs.LogoutDialog;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    ImageView drawerimage, noti, navFooter1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView drawer;
    TextView name;
    LinearLayout l2, l4, l3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        drawerimage = findViewById(R.id.drawerimage);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navview);
        View header = navigationView.getHeaderView(0);
        drawer = header.findViewById(R.id.drawer);
        name = header.findViewById(R.id.name);
        navFooter1 = findViewById(R.id.footer_item_1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        l4 = findViewById(R.id.l4);
        noti = findViewById(R.id.noti);

        hideItem(SelectUserActivity.LoginType);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, MeasurmentsActivity.class));
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

        drawer.setOnClickListener(new View.OnClickListener() {
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

                    case R.id.item7:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, ReportActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item8:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, ComplianceActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.item9:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, RAGActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.item10:
                        //close drawer
                        drawerLayout.closeDrawer(GravityCompat.START);
                        intent = new Intent(DashboardActivity.this, PatientListActivity.class);
                        startActivity(intent);
                        break;

                }

                return true;
            }
        });


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

    private void hideItem(String LoginType) {
        Menu nav_Menu = navigationView.getMenu();
        if (LoginType.equalsIgnoreCase("Patient")) {
            nav_Menu.findItem(R.id.item8).setVisible(false);
            nav_Menu.findItem(R.id.item9).setVisible(false);
            nav_Menu.findItem(R.id.item10).setVisible(false);
            l3.setVisibility(View.VISIBLE);
            l4.setVisibility(View.GONE);
        } else {
            nav_Menu.findItem(R.id.item6).setVisible(false);
            nav_Menu.findItem(R.id.item7).setVisible(false);
            l4.setVisibility(View.VISIBLE);
            l3.setVisibility(View.GONE);

        }
    }


}