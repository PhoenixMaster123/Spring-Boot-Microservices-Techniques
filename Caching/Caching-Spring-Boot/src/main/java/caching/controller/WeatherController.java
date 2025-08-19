package caching.controller;

import caching.entity.Weather;
import caching.service.CachingInspectionService;
import caching.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CachingInspectionService cachingInspectionService;

    @Autowired
    public WeatherController(WeatherService weatherService, CachingInspectionService cachingInspectionService) {
        this.weatherService = weatherService;
        this.cachingInspectionService = cachingInspectionService;
    }

    @GetMapping
    public String getWeatherByCity(@RequestParam String city) {
        return weatherService.getWeatherByCity(city);
    }

    @PostMapping
    public Weather saveWeather(@RequestBody Weather weather) {
        return weatherService.saveWeather(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather() {
        return weatherService.getAllWeather();
    }

    @GetMapping("/cache")
    public void getCacheData() {
        cachingInspectionService.printCacheInfo("weather");
    }

    @PutMapping("/{city}")
    public Weather updateWeather(@PathVariable String city, @RequestBody Weather weather) {
        return weatherService.updateWeather(city, weather);
    }

    @DeleteMapping("/{city}")
    public String deleteWeather(@PathVariable String city) {
        return weatherService.deleteWeather(city);
    }

}
