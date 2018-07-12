package com.example.coolweather;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coolweather.gson.Weather;

import java.util.List;

public class ManageCityAdapter extends RecyclerView.Adapter<ManageCityAdapter.ViewHolder> {

    private static final String TAG = "ManageCityAdapter";
    private List<Weather> weatherList;
    private Activity activity;
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        TextView weatherInfo;
        TextView temperature;
        String weatherId;
        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name_manage);
            weatherInfo = (TextView) itemView.findViewById(R.id.weather_info_manage);
            temperature = (TextView) itemView.findViewById(R.id.temperature_manage);
        }
    }
    public ManageCityAdapter(List<Weather> weathers, Activity activity){
        weatherList = weathers;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(viewHolder);
            }
        });
        viewHolder.temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(viewHolder);
            }
        });
        viewHolder.weatherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(viewHolder);
            }
        });
        return viewHolder;
    }
    private void click(ViewHolder viewHolder){

        Intent intent = new Intent(activity,Weather.class);
        intent.putExtra("weather_id_for_result",viewHolder.weatherId);
        activity.setResult(Activity.RESULT_OK,intent);
        activity.finish();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.cityName.setText(weather.basic.cityName);
        holder.weatherInfo.setText(weather.now.more.info);
        holder.temperature.setText(weather.now.temperature+"℃");
        holder.weatherId = weather.basic.weatherId;
//        holder.cityName.setText(10+"");
//        holder.weatherInfo.setText(10+"");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }


}
