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
import com.example.x_men.storage.model.DataItem;
import com.example.x_men.storage.sample.SampleDataProvider;
import com.example.x_men.storage.util.JSONHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance (this);
        int itemCount = db.dataItemDAO ().countItems ();

        if(itemCount == 0){
            List<DataItem> itemList = JSONHelper.importFromResource (this);
            db.dataItemDAO ().insertAll (itemList);
            Log.i(TAG, "onCreate: data inserted");
        } else{
            Log.i(TAG, "onCreate: data already exists");
        }
//
//        Collections.sort (dataItemList, new Comparator<DataItem> () {
//            @Override
//            public int compare(DataItem o1, DataItem o2) {
//                return o1.getItemName ().compareTo (o2.getItemName ());
//            }
//        });



//      Code to manage sliding navigation drawer
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mCategories = getResources().getStringArray(R.array.categories);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//        mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                R.layout.drawer_list_item, mCategories));
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String category = mCategories[position];
//                Toast.makeText(MainActivity.this, "You chose " + category,
//                        Toast.LENGTH_SHORT).show();
//                mDrawerLayout.closeDrawer(mDrawerList);
//                displayDataItems (category);
//             }
//        });
//      end of navigation drawer

//        mDataSource = new DataSource(this);
//        mDataSource.open();
//        mDataSource.seedDatabase(dataItemList);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = settings.getBoolean(getString(R.string.pref_display_grid), false);

        dataItemList = db.dataItemDAO ().getAll ();
        DataItemAdapter adapter = new DataItemAdapter (this, dataItemList);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvItems);
        if (grid) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

//        displayDataItems(null);
        mRecyclerView.setAdapter (adapter);
    }

//    private void displayDataItems(String category) {
//        listFromDB = mDataSource.getAllItems(category);
//        mItemAdapter = new DataItemAdapter(this, listFromDB);
//        mRecyclerView.setAdapter(mItemAdapter);
//    }

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
        AppDatabase.destoryInstance ();
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
