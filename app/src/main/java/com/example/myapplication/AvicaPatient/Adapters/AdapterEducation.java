package com.example.myapplication.AvicaPatient.Adapters;;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.AvicaPatient.Models.Education;
import com.example.myapplication.AvicaPatient.R;

import java.util.ArrayList;

public class AdapterEducation extends RecyclerView.Adapter<AdapterEducation.MyViewHolder> {

    ArrayList<Education> pamModelArrayList;
    private Context context;
    public LayoutInflater layoutInflater;


    public AdapterEducation(Activity activity, ArrayList<Education> pamModels, Context context) {
        this.pamModelArrayList = pamModels;
        this.context = context;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public AdapterEducation.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterEducation.MyViewHolder(layoutInflater.inflate(R.layout.blogs_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterEducation.MyViewHolder holder, int position) {
        Education pamModel = pamModelArrayList.get(position);

        holder.text_heading.setText(pamModel.title);
        holder.text_discription.setText(pamModel.content);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon);
        Glide.with(context).load(pamModel.uri).apply(options).into(holder.img);



    }

    @Override
    public int getItemCount() {
        return pamModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView text_heading,text_discription;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            text_heading = itemView.findViewById(R.id.text_heading);
            text_discription =  itemView.findViewById(R.id.text_discription);
            img =  itemView.findViewById(R.id.img);

        }


    }
}






