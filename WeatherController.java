package com.weather;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/weather")
    public Map<String, Object> getWeather(@RequestParam double lat, @RequestParam double lon) {
        Map<String, Object> data = service.getWeather(lat, lon);
        double temp = service.getTemperature(data);
        return Map.of(
            "temperature_celsius", temp,
            "temperature_fahrenheit", service.celsiusToFahrenheit(temp),
            "wind_speed_kmh", service.getWindSpeed(data),
            "condition", service.getWeatherDescription((int) data.get("current_weather").get("weathercode")),
            "classification", service.classifyTemperature(temp)
        );
    }
}