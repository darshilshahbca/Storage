package com.example.x_men.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.x_men.storage.model.DataItem;
import com.example.x_men.storage.sample.SampleDataProvider;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private TextView tvOut;
    List<DataItem> dataItemList = SampleDataProvider.dataItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOut = (TextView) findViewById(R.id.out);
        tvOut.setText("");

        Collections.sort (dataItemList, new Comparator<DataItem> () {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getItemName ().compareTo (o2.getItemName ());
            }
        });

        for (DataItem item : dataItemList){
            tvOut.append (item.getItemName () + "\n");
        }

    }
}
