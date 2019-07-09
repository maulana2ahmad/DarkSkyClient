package com.redudant.darkskyclient.services;

import com.redudant.darkskyclient.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    @GET("{lat}, {log}")
    Call<Weather> getWeather(@Path("lat") double lat, @Path("log") double log);
}
