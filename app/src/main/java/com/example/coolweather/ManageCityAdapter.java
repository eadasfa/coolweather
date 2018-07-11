package com.example.coolweather;

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
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView cityName;
        TextView weatherInfo;
        public ViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.city_name_manage);
            weatherInfo = (TextView) itemView.findViewById(R.id.weather_manage);
        }
    }
    public ManageCityAdapter(List<Weather> weathers){
        weatherList = weathers;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.city_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.cityName.setText(weather.basic.cityName);
        holder.weatherInfo.setText(weather.now.temperature);
//        holder.cityName.setText(10+"");
//        holder.weatherInfo.setText(10+"");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }


}
