package com.optimum.Avica.UI.Patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.optimum.Avica.Adapters.CustomAdapter;
import com.optimum.Avica.Models.Item;
import com.optimum.Avica.R;

import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    RecyclerView list1,list2;
    private CustomAdapter customAdapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        list1=findViewById(R.id.list1);
        list2=findViewById(R.id.list2);


        // Sample data
        itemList = new ArrayList<>();
        itemList.add(new Item("Title 1", "Description 1"));
        itemList.add(new Item("Title 2", "Description 2"));
        itemList.add(new Item("Title 3", "Description 3"));

        customAdapter = new CustomAdapter(itemList);
        list1.setAdapter(customAdapter);
        list2.setAdapter(customAdapter);

    }
}