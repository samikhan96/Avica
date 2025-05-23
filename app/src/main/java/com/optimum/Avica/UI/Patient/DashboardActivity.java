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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.optimum.Avica.Models.User;
import com.optimum.Avica.R;
import com.optimum.Avica.UI.Patient.History.HistoryActivity;
import com.optimum.Avica.UI.Patient.TeleMedicine.TelemedActivity;
import com.optimum.Avica.UI.Patient.Dialogs.LogoutDialog;
import com.optimum.Avica.Utils.UserPrefs;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity {

    ImageView drawerimage, noti, navFooter1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    CircleImageView drawer_img,profile_img;
    TextView name,drawer_name,specs,drawer_specs;
    LinearLayout l2, l4, l3;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user= UserPrefs.getGetUser();

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
        l4 = findViewById(R.id.l4);
        noti = findViewById(R.id.noti);

        name.setText(user.first_name +" "+user.last_name);
        drawer_name.setText(user.first_name +" "+user.last_name);
        specs.setText(user.username);
        drawer_specs.setText(user.username);
        // Set Text Values+
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(DashboardActivity.this).load(user.uri).apply(options).into(drawer_img);
        Glide.with(DashboardActivity.this).load(user.uri).apply(options).into(profile_img);

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

        drawer_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SelectUserActivity.LoginType.equalsIgnoreCase("patient")) {
                    Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (SelectUserActivity.LoginType.equalsIgnoreCase("doctor")) {
                    Intent intent = new Intent(DashboardActivity.this, DocProfileActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }


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