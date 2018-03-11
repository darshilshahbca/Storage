package com.example.x_men.storage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x_men.storage.database.AppDatabase;
import com.example.x_men.storage.events.DataItemEvent;
import com.example.x_men.storage.events.DataItemsEvent;
import com.example.x_men.storage.model.DataItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings("FieldCanBeLocal")
public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvDescription, tvPrice;
    private ImageView itemImage;

    private AppDatabase db;

    private Executor executor = Executors.newSingleThreadExecutor ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = AppDatabase.getInstance (this);

        executor.execute (new Runnable () {
            @Override
            public void run() {
                String itemId = getIntent ().getStringExtra (DataItemAdapter.ITEM_ID_KEY);
                DataItem item = db.dataItemDAO ().findById (itemId);
                EventBus.getDefault ().post (new DataItemEvent (item));
            }
        });

        tvName = (TextView) findViewById(R.id.tvItemName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        itemImage = (ImageView) findViewById(R.id.itemImage);

        EventBus.getDefault ().register (this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataItemEventHandler(DataItemEvent event) {
        DataItem item = event.getDataItem ();
        tvName.setText(item.getItemName());
        tvDescription.setText(item.getDescription());

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tvPrice.setText(nf.format(item.getPrice()));

        InputStream inputStream = null;
        try {
            String imageFile = item.getImage();
            inputStream = getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            itemImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance ();
        EventBus.getDefault ().unregister (this);
        super.onDestroy ();
    }
}


