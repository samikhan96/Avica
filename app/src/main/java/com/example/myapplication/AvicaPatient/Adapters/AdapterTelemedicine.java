package com.example.myapplication.AvicaPatient.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentData;
import com.example.myapplication.AvicaPatient.Models.Appointment.AppointmentModel;
import com.example.myapplication.AvicaPatient.Models.Reports;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.UI.Patient.JoinMeetingActivity;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import java.util.ArrayList;

public class AdapterTelemedicine extends RecyclerView.Adapter<AdapterTelemedicine.MyViewHolder> {

    ArrayList<AppointmentModel> pamModelArrayList;
    private Context context;
    public LayoutInflater layoutInflater;


    public AdapterTelemedicine(Activity activity, ArrayList<AppointmentModel> pamModels, Context context) {
        this.pamModelArrayList = pamModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public AdapterTelemedicine.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterTelemedicine.MyViewHolder(layoutInflater.inflate(R.layout.appointment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterTelemedicine.MyViewHolder holder, int position) {
        AppointmentModel pamModel = pamModelArrayList.get(position);

        holder.tv_1.setText(pamModel.patient_name);
        holder.tv_2.setText(pamModel.doctor_name);
        holder.tv_4.setText(pamModel.status);
        AppUtils.setFormattedDate(pamModel.AppointmetDate,holder.tv_3);

        // Handle "View" button click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, JoinMeetingActivity.class);
            intent.putExtra("appointment", pamModel); // Pass the appointment object including ID
            context.startActivity(intent); // Start the JoinMeetingActivity
        });


    }

    @Override
    public int getItemCount() {
        return pamModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_1,tv_2,tv_3,tv_4;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 =  itemView.findViewById(R.id.tv_2);
            tv_3 =  itemView.findViewById(R.id.tv_3);
            tv_4 =  itemView.findViewById(R.id.tv_4);

        }


    }
}








