package com.example.x_men.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.x_men.storage.model.DataItem;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private TextView tvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataItem item =
                new DataItem (null, "My menu item", "a description", "a category", 1, 9.95, "apple_pie.jpg"   );

        tvOut = (TextView) findViewById(R.id.out);
        tvOut.setText(item.toString ());

    }
}
