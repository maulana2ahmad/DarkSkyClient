package com.redudant.darkskyclient.services;

import android.util.Log;
import android.widget.Toast;

import com.redudant.darkskyclient.activity.MainActivity;
import com.redudant.darkskyclient.events.ErrorEvent;
import com.redudant.darkskyclient.events.WeatherEvent;
import com.redudant.darkskyclient.models.Currently;
import com.redudant.darkskyclient.models.Weather;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static final String TAG = MainActivity.class.getSimpleName();

    //https://api.darksky.net/forecast/f0f6756cd3ccee165333fa3b80c4bed3/37.8267,-122.4233
    private static Retrofit retrofit = null;

    //opsi 1
    //private static final String BASE_URL = "https://api.darksky.net/forecast/f0f6756cd3ccee165333fa3b80c4bed3/37.8267,-122.4233/";

    //opsi 2
    private static final String BASE_URL = "https://api.darksky.net/forecast/f0f6756cd3ccee165333fa3b80c4bed3/";

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                               .baseUrl(BASE_URL)
                               .addConverterFactory(GsonConverterFactory.create())
                               .build();
        }
        return retrofit;
    }

    public void loadWeather(double lat, double log)
    {
        WeatherService apiServices =
                getClient().create(WeatherService.class);

        Call<Weather> call = apiServices.getWeather(lat, log);
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

                Weather weather = response.body();
                if (weather != null) {
                    Currently currently = weather.getCurrently();
                    Log.e(TAG, "Temperature: " + currently.getTemperature());
                    EventBus.getDefault().post(new WeatherEvent(weather));
                }
                else
                {
                    EventBus.getDefault().post(new ErrorEvent("No Weather data available"));

                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

                EventBus.getDefault().post(new ErrorEvent("Unable connect weather server"));
                //Toast.makeText(this, "Unable connect weather server", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
