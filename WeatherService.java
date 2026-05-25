package com.weather;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";

    public Map<String, Object> getWeather(double latitude, double longitude) {
        RestTemplate rest = new RestTemplate();
        String url = API_URL + "?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true&hourly=relative_humidity_2m";
        return rest.getForObject(url, Map.class);
    }

    public double getTemperature(Map<String, Object> weatherData) {
        Map<String, Object> current = (Map<String, Object>) weatherData.get("current_weather");
        return (double) current.get("temperature");
    }

    public double getWindSpeed(Map<String, Object> weatherData) {
        Map<String, Object> current = (Map<String, Object>) weatherData.get("current_weather");
        return (double) current.get("windspeed");
    }

    public String getWeatherDescription(int code) {
        Map<Integer, String> map = Map.of(
            0, "Clear sky",
            1, "Mainly clear",
            2, "Partly cloudy",
            3, "Overcast",
            61, "Slight rain",
            63, "Moderate rain",
            95, "Thunderstorm"
        );
        return map.getOrDefault(code, "Unknown condition");
    }

    public double celsiusToFahrenheit(double celsius) {
        return Math.round((celsius * 9.0 / 5.0 + 32.0) * 100.0) / 100.0;
    }

    public boolean isStrongWind(double windSpeedKmh) {
        return windSpeedKmh > 50;
    }

    public String classifyTemperature(double celsius) {
        if (celsius < 0) return "Freezing";
        if (celsius < 10) return "Cold";
        if (celsius < 20) return "Mild";
        if (celsius < 30) return "Warm";
        return "Hot";
    }
}