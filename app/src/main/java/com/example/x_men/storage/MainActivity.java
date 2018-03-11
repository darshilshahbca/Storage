package com.example.x_men.storage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.x_men.storage.database.AppDatabase;
import com.example.x_men.storage.database.DataSource;
import com.example.x_men.storage.events.DataItemsEvent;
import com.example.x_men.storage.model.DataItem;
import com.example.x_men.storage.sample.SampleDataProvider;
import com.example.x_men.storage.util.JSONHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREFS = "my_global_prefs";
    private static final String TAG = "MainActivity";
    List<DataItem> dataItemList;
//            = SampleDataProvider.dataItemList;
//
//    DataSource mDataSource;
//    List<DataItem> listFromDB;
//    DrawerLayout mDrawerLayout;
//    ListView mDrawerList;
//    String[] mCategories;
    RecyclerView mRecyclerView;
//    DataItemAdapter mItemAdapter;

    private AppDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance (this);

        executor.execute (new Runnable () {
            @Override
            public void run() {
                int itemCount = db.dataItemDAO ().countItems ();
                if(itemCount == 0){
                    List<DataItem> itemList = JSONHelper.importFromResource (MainActivity.this);
                    db.dataItemDAO ().insertAll (itemList);
                    Log.i(TAG, "onCreate: data inserted");
                } else{
                    Log.i(TAG, "onCreate: data already exists");
                }
            }
        });


        executor.execute (new Runnable () {
            @Override
            public void run() {
                dataItemList = db.dataItemDAO ().getAll ();
                EventBus.getDefault ().post (new DataItemsEvent (dataItemList));
            }
        });

        EventBus.getDefault ().register (this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dataItemsEventHandler(DataItemsEvent event) {

        dataItemList = event.getItemList ();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = settings.getBoolean(getString(R.string.pref_display_grid), false);

        DataItemAdapter adapter = new DataItemAdapter (this, dataItemList);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems);
        if (grid) {
            mRecyclerView.setLayoutManager(new GridLayoutManager (this, 3));
        }

        mRecyclerView.setAdapter (adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
//        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mDataSource.open();
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance ();
        EventBus.getDefault ().unregister (this);
        super.onDestroy ();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signin:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
                return true;
            case R.id.action_settings:
                // Show the settings screen
                Intent settingsIntent = new Intent(this, PrefsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_all_items:
                // display all items
//                displayDataItems(null);
                return true;
            case R.id.action_choose_category:
                //open the drawer
//                mDrawerLayout.openDrawer(mDrawerList);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(SigninActivity.EMAIL_KEY);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor editor =
                    getSharedPreferences(MY_GLOBAL_PREFS, MODE_PRIVATE).edit();
            editor.putString(SigninActivity.EMAIL_KEY, email);
            editor.apply();

        }

    }

}
