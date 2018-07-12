package com.example.coolweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolweather.gson.Basic;
import com.example.coolweather.gson.Now;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ManageCityFragment extends Fragment {
    private static final String TAG = "ManageCityFragment";
    public static final String SELECT_ANOTHER_CITY = "select_another_city";
    private RecyclerView cityRecycler ;
    private FloatingActionButton addCityButton;
    private List<Weather> weatherList = new ArrayList<>();
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_city,container,false);
        cityRecycler = (RecyclerView) view.findViewById(R.id.manage_city_recycler_view);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = prefs.edit();
        addCityButton = (FloatingActionButton) view.findViewById(R.id.add_city);
        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cityRecycler.setLayoutManager(layoutManager);
        ManageCityAdapter adapter = new ManageCityAdapter(weatherList,getActivity());
        cityRecycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra(SELECT_ANOTHER_CITY,SELECT_ANOTHER_CITY);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void initList(){
//        Random random = new Random();
//        for(int i=0;i<50;i++){
//            Weather weather = new Weather();
//            weather.basic = new Basic();
//            weather.basic.cityName = random.nextInt(1000)+"";
//            weather.now = new Now();
//            weather.now.temperature = random.nextInt(40)+"";
//            weatherList.add(weather);
//        }
        String currentWeatherId = getActivity().getIntent().getStringExtra("currentWeatherId");

        String currentWeatherInfo = getActivity().getIntent().getStringExtra(currentWeatherId);
        String allWeathers = prefs.getString("all_weathers",null);
        Log.d(TAG, "allWeathers: "+allWeathers);
        Log.d(TAG, "currentWeather: "+currentWeatherId);
        if(allWeathers==null){
            allWeathers = currentWeatherId;
        }else if(!allWeathers.contains(currentWeatherId)){
            allWeathers+=" "+ currentWeatherId;
        }
        editor.putString(currentWeatherId,currentWeatherInfo);
        editor.putString("all_weathers",allWeathers);
        editor.apply();
        String weathers[] = allWeathers.split(" ");
        Log.d(TAG, "initList: weathers[]:"+ Arrays.toString(weathers));
        for (String ws: weathers){
            String weatherInfo = prefs.getString(ws,null);
            Weather weather = Utility.handleWeatherResponse(weatherInfo);
            Log.d(TAG, "currentId:"+ws+" city"+weather.basic.cityName);
            weatherList.add(weather);
        }
    }
}
