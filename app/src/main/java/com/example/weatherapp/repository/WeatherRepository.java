package com.example.weatherapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.apputils.Constants;
import com.example.weatherapp.model.Forecast;
import com.example.weatherapp.retrofit.ApiClient;
import com.example.weatherapp.retrofit.ApiInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private static WeatherRepository weatherRepository;
    private ApiInterface apiService;

    public static WeatherRepository getInstance() {
        if (weatherRepository == null) {
            weatherRepository = new WeatherRepository();
        }
        return weatherRepository;
    }

    public WeatherRepository() {
        apiService = ApiClient.getInstance().getApi();
    }

    public MutableLiveData<Forecast> getWeatherForeCast() {
        MutableLiveData<Forecast> weatherData = new MutableLiveData<>();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(Constants.PARAM_CITY, Constants.CITY);
        requestBody.put(Constants.PARAM_APP_ID, Constants.APP_ID);
        requestBody.put(Constants.PARAM_UNITS, Constants.UNITS);
        apiService.getWeatherForeCast(requestBody).enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call,
                                   Response<Forecast> response) {
                if (response.isSuccessful()) {
                    weatherData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                weatherData.setValue(null);
            }
        });
        return weatherData;
    }
}
