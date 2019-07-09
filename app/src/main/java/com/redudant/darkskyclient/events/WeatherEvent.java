package com.redudant.darkskyclient.events;

import com.redudant.darkskyclient.models.Weather;

public class WeatherEvent {

    private final Weather temperature;

    public WeatherEvent(Weather weather) {
        this.temperature = weather;
    }

    public Weather getTemperature() {
        return temperature;
    }
}
