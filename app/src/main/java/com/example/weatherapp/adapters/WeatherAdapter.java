package com.example.weatherapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ItemForecastBinding;
import com.example.weatherapp.model.ForeCastList;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.Date;
import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    List<ForeCastList> foreCastLists;
    ItemForecastBinding itemBinding;


    public WeatherAdapter(List<ForeCastList> itemlist) {
        this.foreCastLists = itemlist;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_forecast, parent, false);
        return new WeatherAdapter.ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {

        String celcius = "\u00B0";
        ForeCastList foreCastList = foreCastLists.get(position);
        holder.itemBinding.tvDate.setText(setDate(foreCastList.getDtTxt()));
        holder.itemBinding.tvWeatherInfo.setText(foreCastList.getWeather().get(0).getDescription());
        holder.itemBinding.tvTemp.setText(foreCastList.getMain().getTemp_min() + celcius + "/" + foreCastList.getMain().getTemp_max() + celcius);
    }

    @Override
    public int getItemCount() {
        return foreCastLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemForecastBinding itemBinding;

        public ViewHolder(@NonNull ItemForecastBinding itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }
    }

    public String setDate(String strDate) {
        Date date = DateTimeUtils.formatDate(strDate);
        return DateTimeUtils.formatWithPattern(date, "E, MMMM dd");
    }
}
