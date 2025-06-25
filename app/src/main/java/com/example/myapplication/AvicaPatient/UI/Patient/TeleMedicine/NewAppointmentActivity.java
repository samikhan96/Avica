package com.example.myapplication.AvicaPatient.UI.Patient.TeleMedicine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.AvicaPatient.HttpUtils.AppServices;
import com.example.myapplication.AvicaPatient.Listener.ServiceListener;
import com.example.myapplication.AvicaPatient.Models.DoctorProfile.ProfileData;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.Dialogs.AddPHRDialog;
import com.example.myapplication.AvicaPatient.UI.Patient.Dialogs.Appointment_Create_Doalog;
import com.example.myapplication.AvicaPatient.UI.Patient.History.AddDiagnosis;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NewAppointmentActivity extends AppCompatActivity {

    EditText et_1,et_2,et_3,et_4,et_5;
    ArrayList<ProfileData> doctorList = new ArrayList<>();
    ArrayList<ProfileData> doctorListnew = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
        ImageView back = findViewById(R.id.back);
        Button loginBtn = findViewById(R.id.loginBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        et_1=findViewById(R.id.et_1);
        et_2=findViewById(R.id.et_2);
        et_3=findViewById(R.id.et_3);
        et_4=findViewById(R.id.et_4);
        et_5=findViewById(R.id.et_5);

        et_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDoctors();
            }
        });
        et_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(et_3);
            }
        });
        et_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(et_3);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_1.getText().toString().equalsIgnoreCase("")
                        ||et_2.getText().toString().equalsIgnoreCase("")
                        ||et_3.getText().toString().equalsIgnoreCase("")
                        ||et_4.getText().toString().equalsIgnoreCase("")
                        ||et_5.getText().toString().equalsIgnoreCase("") ){
                    AppUtils.Toast("Please fill all fields");
                }
                else {
                    CreateAppointment();

                }

            }
        });

    }

    public void CreateAppointment(){

        AppUtils.showProgressDialog(NewAppointmentActivity.this);

        // Create the JSON object
        JSONObject json = new JSONObject();
        try {
            json.put("title", et_1.getText().toString());
            json.put("description", et_5.getText().toString());
            json.put("start_date", "2025-05-24T02:00:00Z");
            json.put("end_date", "2025-05-24T02:10:10Z");
            json.put("type", "ONLINE");
            json.put("about", "CONSULTANCY");

            // Add users array
            JSONArray users = new JSONArray();
            users.put(selectedDoctorId);
            json.put("users", users);

        } catch (Exception e) {
            e.printStackTrace();
        }

        AppServices.CreateAppointment(NewAppointmentActivity.class.getSimpleName(), json, new ServiceListener<String, String>() {
            @Override
            public void success(String success) {
                AppUtils.dismisProgressDialog(NewAppointmentActivity.this);
                Appointment_Create_Doalog cdd = new Appointment_Create_Doalog(NewAppointmentActivity.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(NewAppointmentActivity.this);

            }
        });
    }

    public void getDoctors(){
        AppUtils.showProgressDialog(NewAppointmentActivity.this);

        AppServices.getDoctors(NewAppointmentActivity.class.getSimpleName(), new ServiceListener<ArrayList<ProfileData>, String>() {
            @Override
            public void success(ArrayList<ProfileData> success) {
                AppUtils.dismisProgressDialog(NewAppointmentActivity.this);
                doctorList=success;
                try {
                    for (int i = 0; i < doctorList.size(); i++) {
                        ProfileData profileData;
                        profileData=doctorList.get(i);
                        String id = profileData.getId();
                        String firstName = profileData.getFirstName();
                        String lastName = profileData.getLastName();

                        String fullName = firstName + " " + lastName;
                        doctorListnew.add(new ProfileData(id, fullName));
                    }

                    runOnUiThread(() -> showDoctorDialog());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void error(String error) {
                AppUtils.dismisProgressDialog(NewAppointmentActivity.this);

            }
        });

    }

    private ProfileData selectedDoctor = null;
    private String selectedDoctorId = ""; // Declare at class level

    private void showDoctorDialog() {
        CharSequence[] doctorNames = new CharSequence[doctorListnew.size()];
        for (int i = 0; i < doctorListnew.size(); i++) {
            doctorNames[i] = doctorListnew.get(i).getFirstName();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Doctor");
        builder.setItems(doctorNames, (dialog, which) -> {
            selectedDoctor = doctorListnew.get(which);
            et_2.setText(selectedDoctor.getFirstName());
            selectedDoctorId = selectedDoctor.getId(); // make sure getId() exists in Doctor model
        });
        builder.show();
    }
    private void showDatePickerDialog(EditText targetEditText) {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is 0-based, so +1
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    targetEditText.setText(selectedDate); // Set selected date to EditText
                }, year, month, day);

        datePickerDialog.show();
    }
    private void showTimePickerDialog(EditText targetEditText) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String amPm;
                    if (selectedHour >= 12) {
                        amPm = "PM";
                        if (selectedHour > 12) selectedHour -= 12;
                    } else {
                        amPm = "AM";
                        if (selectedHour == 0) selectedHour = 12;
                    }

                    String formattedTime = String.format(Locale.getDefault(), "%02d:%02d %s", selectedHour, selectedMinute, amPm);
                    targetEditText.setText(formattedTime);
                }, hour, minute, false); // false = 12-hour format

        timePickerDialog.show();
    }

}