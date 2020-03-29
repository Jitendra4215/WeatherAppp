package com.example.weatherapp.retrofit;

import com.example.weatherapp.model.Forecast;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @POST("forecast")
    Call<Forecast> getWeatherForeCast(@QueryMap Map<String, String> request);
}
