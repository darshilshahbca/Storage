package com.example.x_men.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.x_men.storage.model.DataItem;
import com.example.x_men.storage.sample.SampleDataProvider;

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

        for (DataItem item : dataItemList){
            tvOut.append (item.getItemName () + "\n");
        }

    }
}
