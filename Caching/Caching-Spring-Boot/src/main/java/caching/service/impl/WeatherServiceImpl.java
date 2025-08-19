package caching.service.impl;

import caching.entity.Weather;
import caching.repository.WeatherRepository;
import caching.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final static Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }


    @Override
    @Cacheable(value = "weather", key = "#city") // In the memory cache (not the best for production -> use Redis)
    public String getWeatherByCity(String city) {
        logger.info("Fetching weather from DB for city: {}", city);
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data is not available");
    }

    @Override
    public Weather saveWeather(Weather weather) {
        return weatherRepository.save(weather);
    }

    @Override
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @Override
    @CachePut(value = "weather", key = "#city")
    public Weather updateWeather(String city, Weather weather) {
        logger.info("Updating weather for city: {}", city);
        Optional<Weather> existingWeather = weatherRepository.findByCity(city);
        if (existingWeather.isPresent()) {
            Weather updatedWeather = existingWeather.get();
            updatedWeather.setForecast(weather.getForecast());
            return weatherRepository.save(updatedWeather);
        } else {
            logger.warn("No weather data found for city: {}", city);
            return null; // or throw an exception
        }
    }

    @Override
    @CacheEvict(value = "weather", key = "#city")
    public String deleteWeather(String city) {
        logger.info("Deleting weather data for city: {}", city);
        Optional<Weather> existingWeather = weatherRepository.findByCity(city);
        if (existingWeather.isPresent()) {
            weatherRepository.delete(existingWeather.get());
            return "Weather data for city: " + city + " has been deleted.";
        } else {
            return "There is no weather data for city: " + city;
        }
    }
}
