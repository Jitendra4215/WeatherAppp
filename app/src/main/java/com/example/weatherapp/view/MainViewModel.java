package com.example.weatherapp.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.repository.WeatherRepository;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Forecast> mutableLiveData;
    private WeatherRepository weatherRepository;

    public void init() {
        weatherRepository = WeatherRepository.getInstance();
        mutableLiveData = weatherRepository.getWeatherForeCast();
    }

    public LiveData<Forecast> getWeatherRepository() {
        return mutableLiveData;
    }
}
