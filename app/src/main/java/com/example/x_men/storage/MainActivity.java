package com.example.x_men.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.x_men.storage.model.DataItem;
import com.example.x_men.storage.sample.SampleDataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    List<DataItem> dataItemList = SampleDataProvider.dataItemList;
    List<String> itemNames = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (DataItem item : dataItemList){
//            tvOut.append (item.getItemName () + "\n");
            itemNames.add(item.getItemName ());
        }

        Collections.sort (itemNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
            this, android.R.layout.simple_list_item_1, itemNames);

        ListView listView = findViewById (android.R.id.list);
        listView.setAdapter (adapter);

    }
}
