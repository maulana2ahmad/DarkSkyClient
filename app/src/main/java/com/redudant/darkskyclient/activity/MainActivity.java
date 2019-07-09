package com.redudant.darkskyclient.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redudant.darkskyclient.R;
import com.redudant.darkskyclient.events.ErrorEvent;
import com.redudant.darkskyclient.events.WeatherEvent;
import com.redudant.darkskyclient.models.Currently;
import com.redudant.darkskyclient.models.Weather;
import com.redudant.darkskyclient.services.Client;
import com.redudant.darkskyclient.services.WeatherService;
import com.redudant.darkskyclient.util.WeatherIconUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.tv_Temp)
    TextView tv_Temp;

    @BindView(R.id.iv_icons)
    ImageView iv_icons;

    @BindView(R.id.tv_summary)
    TextView tv_summry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestCurrentWeather(37.8267, -122.4233);

        ButterKnife.bind(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    //menghilangkan nilai koma dibelakang (Math.round)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WeatherEvent weatherEvent)
    {
       Currently currently = weatherEvent.getTemperature().getCurrently();
       tv_Temp.setText(String.valueOf(Math.round(currently.getTemperature())));
       tv_summry.setText(currently.getSummary());

       //array denga Map data di dapat dari package util
       iv_icons.setImageResource(WeatherIconUtil.ICON.get(currently.getIcon()));
    }

    //massage error
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void errorEvend(ErrorEvent errorEvent)
    {
        Toast.makeText(this, errorEvent.getErrorMassage(), Toast.LENGTH_SHORT).show();
    }


    private void requestCurrentWeather(double lat, double log) {

        Client weatherClient = new Client();
        weatherClient.loadWeather(lat, log);
    }


    /**
    public void loadWeather()
    {
        WeatherService apiServices =
                Client.getClient().create(WeatherService.class);

        Call<Weather> call = apiServices.getWeather();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {

               Currently currently = response.body().getCurrently();
                Log.e(TAG, "Temperature: " + currently.getTemperature() );
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });

    }
     */
}
