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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Collections.sort (dataItemList, new Comparator<DataItem> () {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName ().compareTo (o2.getItemName ());
            }
        });

        DataItemAdapter adapter = new DataItemAdapter (this, dataItemList);

        ListView listView = findViewById (android.R.id.list);
        listView.setAdapter (adapter);

    }
}
