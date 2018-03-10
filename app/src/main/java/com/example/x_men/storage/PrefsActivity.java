package com.example.x_men.storage;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PrefsActivity extends AppCompatActivity {

    private static final String TAG = "PrefsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        getFragmentManager ()
                .beginTransaction ()
                .add(R.id.prefs_content, new SettingFragment ())
                .commit ();

    }

    public static class SettingFragment extends PreferenceFragment{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate (savedInstanceState);
            addPreferencesFromResource (R.xml.settings);
        }
    }

}

