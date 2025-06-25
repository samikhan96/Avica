package com.example.myapplication.AvicaPatient.UI.Patient;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentModel;
import com.example.myapplication.AvicaPatient.R;

public class JoinMeetingActivity extends AppCompatActivity {

    private Button btnJoin;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_join_meeting);

        // Find buttons
        ImageView buttonBack = findViewById(R.id.back_button);

        // Set click listener for the back button
        buttonBack.setOnClickListener(v -> finish());

        // Initialize views (including btnJoin)
        initializeViews();

        // Retrieve the appointment data from the Intent
        AppointmentModel appointment = (AppointmentModel) getIntent().getSerializableExtra("appointment");

        // Log the appointment data
        if (appointment != null) {
            Log.d("JoinMeetingActivity", "Appointment Id: " + appointment.getId());
            Log.d("JoinMeetingActivity", "Appointment Title: " + appointment.getTitle());
            Log.d("JoinMeetingActivity", "Appointment Description: " + appointment.getDescription());
            Log.d("JoinMeetingActivity", "Appointment Type: " + appointment.getType());
            Log.d("JoinMeetingActivity", "Appointment About: " + appointment.getAbout());
            Log.d("JoinMeetingActivity", "Start Date: " + appointment.getAppointmetDate());
            Log.d("JoinMeetingActivity", "End Date: " + appointment.getAppointmetDate());

            // Set the text for each TextView
            ((TextView) findViewById(R.id.tvAppointmentId)).setText(appointment.getId());
            ((TextView) findViewById(R.id.tvAppointmentTitle)).setText(appointment.getTitle());
            ((TextView) findViewById(R.id.tvAppointmentDescription)).setText(appointment.getDescription());
            ((TextView) findViewById(R.id.tvAppointmentType)).setText(appointment.getType());
            ((TextView) findViewById(R.id.tvAbout)).setText(appointment.getAbout());
            ((TextView) findViewById(R.id.tvStartDate)).setText(appointment.getAppointmetDate());
            ((TextView) findViewById(R.id.tvEndDate)).setText(appointment.getAppointmetDate());

            // Log participant details

            // Store the appointment ID in a member variable for later use
            final String appointmentId = appointment.getId();

            // Set OnClickListener for Join button
            btnJoin.setOnClickListener(view -> {
                Log.d(JoinMeetingActivity.class.getSimpleName(), "Join Meeting button clicked");
                Intent intent = new Intent(JoinMeetingActivity.this, VideoCall_Activity.class);
                // Pass the appointment ID to the next activity
                intent.putExtra("appointmentId", appointmentId);
                startActivity(intent);
            });

        } else {
            Log.e("JoinMeetingActivity", "No appointment data found in the intent.");
            Toast.makeText(this, "No appointment data available.", Toast.LENGTH_SHORT).show();
        }
    }

    // Initialize the button and any other views
    private void initializeViews() {
        btnJoin = findViewById(R.id.btn_join);
        Log.d(JoinMeetingActivity.class.getSimpleName(), "Views initialized in initializeViews");
    }

}

