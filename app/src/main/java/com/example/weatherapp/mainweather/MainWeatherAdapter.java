package com.example.weatherapp.mainweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainWeatherAdapter extends RecyclerView.Adapter<MainWeatherAdapter.MainWeatherViewHolder>{
    private Context context;
    private List<MainWeather> MWList;

    public MainWeatherAdapter(Context context) {
        this.context = context;
    }

    public MainWeatherAdapter(List<MainWeather> mainWeatherList) {
        MWList = mainWeatherList;
    }

    public void setData(List<MainWeather> list){
        this.MWList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MainWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mainweather, parent,false);
        return new MainWeatherAdapter.MainWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainWeatherViewHolder holder, int position) {
        MainWeather mainWeather = MWList.get(position);
        if(mainWeather == null){
            return;
        }
        //holder.imgMW.setImageResource((mainWeather.getImgMW()));

        Picasso.get().load(mainWeather.getImgMW()).into(holder.imgMW);
        holder.tv_dayMW.setText(mainWeather.getDayMW());
        holder.tv_tempMW.setText(mainWeather.getTempMW());
    }

    @Override
    public int getItemCount() {
        if(MWList != null){
            return MWList.size();
        }
        return 0;
    }

    public class MainWeatherViewHolder extends RecyclerView.ViewHolder{

        ImageView imgMW;
        TextView tv_tempMW, tv_dayMW, tv_rainchanceMW;
        public MainWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMW = itemView.findViewById(R.id.iv_mainweather);
            tv_dayMW = itemView.findViewById(R.id.tv_daymain);
            tv_tempMW = itemView.findViewById(R.id.tv_tempmain);
        }
    }
}
