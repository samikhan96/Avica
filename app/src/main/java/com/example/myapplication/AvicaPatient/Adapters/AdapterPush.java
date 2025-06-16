package com.example.myapplication.AvicaPatient.Adapters;;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AvicaPatient.Models.Notifications;
import com.example.myapplication.AvicaPatient.R;

import java.util.ArrayList;

public class AdapterPush extends RecyclerView.Adapter<AdapterPush.MyViewHolder> {

    ArrayList<Notifications> pamModelArrayList;
    private Context context;
    public LayoutInflater layoutInflater;


    public AdapterPush(Activity activity, ArrayList<Notifications> pamModels, Context context) {
        this.pamModelArrayList = pamModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public AdapterPush.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterPush.MyViewHolder(layoutInflater.inflate(R.layout.pushnoti_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterPush.MyViewHolder holder, int position) {
        Notifications pamModel = pamModelArrayList.get(position);

        holder.text_heading.setText(pamModel.title);
        holder.text_discription.setText(pamModel.description);



    }

    @Override
    public int getItemCount() {
        return pamModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text_heading,text_discription;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_heading = itemView.findViewById(R.id.text_heading);
            text_discription =  itemView.findViewById(R.id.text_discription);

        }


    }
}





