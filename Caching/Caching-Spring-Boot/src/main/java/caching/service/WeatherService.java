package caching.service;

import caching.entity.Weather;

import java.util.List;

public interface WeatherService {
    String getWeatherByCity(String city);

    Weather saveWeather(Weather weather);

    List<Weather> getAllWeather();

    Weather updateWeather(String city, Weather weather);

    String deleteWeather(String city);
}
