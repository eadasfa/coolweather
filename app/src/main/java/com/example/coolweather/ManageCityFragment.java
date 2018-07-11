package com.example.coolweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coolweather.gson.Basic;
import com.example.coolweather.gson.Now;
import com.example.coolweather.gson.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ManageCityFragment extends Fragment {
    private static final String TAG = "ManageCityFragment";
    private RecyclerView cityRecycler ;
    private List<Weather> weatherList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_city,container,false);
        cityRecycler = (RecyclerView) view.findViewById(R.id.manage_city_recycler_view);
        initList();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        cityRecycler.setLayoutManager(layoutManager);
        ManageCityAdapter adapter = new ManageCityAdapter(weatherList);
        cityRecycler.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initList(){
        Random random = new Random();
        for(int i=0;i<50;i++){
            Weather weather = new Weather();
            weather.basic = new Basic();
            weather.basic.cityName = random.nextInt(1000)+"";
            weather.now = new Now();
            weather.now.temperature = random.nextInt(40)+"";
            weatherList.add(weather);
        }
    }
}
