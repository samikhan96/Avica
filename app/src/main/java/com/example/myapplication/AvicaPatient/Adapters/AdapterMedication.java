package com.example.myapplication.AvicaPatient.Adapters;;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AvicaPatient.Models.Medication;
import com.example.myapplication.AvicaPatient.R;
import com.example.myapplication.AvicaPatient.Utils.AppUtils;

import java.util.ArrayList;

public class AdapterMedication extends RecyclerView.Adapter<AdapterMedication.MyViewHolder> {

    ArrayList<Medication> pamModelArrayList;
    private Context context;
    public LayoutInflater layoutInflater;


    public AdapterMedication(Activity activity, ArrayList<Medication> pamModels, Context context) {
        this.pamModelArrayList = pamModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public AdapterMedication.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterMedication.MyViewHolder(layoutInflater.inflate(R.layout.medication_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterMedication.MyViewHolder holder, int position) {
        Medication pamModel = pamModelArrayList.get(position);

        holder.tv_1.setText(pamModel.name);
        holder.tv_2.setText(pamModel.dosage);
        holder.tv_3.setText(pamModel.frequency);
        AppUtils.setFormattedDate(pamModel.start_date,holder.tv_4);
        AppUtils.setFormattedDate(pamModel.end_date,holder.tv_5);
        AppUtils.setFormattedTime(pamModel.time,holder.tv_6);



    }

    @Override
    public int getItemCount() {
        return pamModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_1 = itemView.findViewById(R.id.tv_1);
            tv_2 =  itemView.findViewById(R.id.tv_2);
            tv_3 =  itemView.findViewById(R.id.tv_3);
            tv_4 =  itemView.findViewById(R.id.tv_4);
            tv_5 =  itemView.findViewById(R.id.tv_5);
            tv_6 =  itemView.findViewById(R.id.tv_6);

        }


    }
}






