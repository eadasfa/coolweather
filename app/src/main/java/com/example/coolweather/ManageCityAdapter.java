package com.example.coolweather;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        viewHolder.cityName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick(viewHolder);
                return true;
            }
        });
        viewHolder.weatherInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick(viewHolder);
                return true;
            }
        });
        viewHolder.temperature.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                longClick(viewHolder);
                return true;
            }
        });

        return viewHolder;
    }
    private void longClick(final ViewHolder viewHolder){
        Log.d(TAG+"long", "longClick:enter ");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Warning");
        builder.setMessage("是否删除城市:"+weatherList.get(viewHolder.getAdapterPosition()).basic.cityName+"?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                weatherList.remove(viewHolder.getAdapterPosition());
                //将缓存数据中的城市删除
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove(viewHolder.weatherId);
                String allWeathers = pref.getString("all_weathers",null);
                Log.d(TAG+"long", allWeathers);
                if(allWeathers!=null){
                    allWeathers.replace(viewHolder.weatherId,"");
                    editor.putString("all_weathers",allWeathers);
                    editor.apply();
                }
                if(weatherList.size()==0){
                    Intent intent = new Intent(activity,MainActivity.class);
                    intent.putExtra(ManageCityFragment.SELECT_ANOTHER_CITY,ManageCityFragment.SELECT_ANOTHER_CITY);
                    activity.startActivity(intent);
                    activity.finish();
                }
                //移除在界面中的listView
                notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        builder.setCancelable(false);
        builder.show();
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
