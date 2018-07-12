package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.coolweather.gson.Weather;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String judge = getIntent().getStringExtra(ManageCityFragment.SELECT_ANOTHER_CITY);
        if(judge==null||!judge.equals(ManageCityFragment.SELECT_ANOTHER_CITY)) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            Log.d(TAG, "weather_id=" + prefs.getString("weather_id", null));
            if (prefs.getString("weather_id", null) != null) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
