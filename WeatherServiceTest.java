package com.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.weather.WeatherService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private WeatherService service;

    private final Map<String, Object> mockResponse = Map.of(
        "current_weather", Map.of(
            "temperature", 22.5,
            "windspeed", 18.3,
            "weathercode", 2
        )
    );

    // Passing tests
    @Test
    void testGetTemperature() {
        assertEquals(22.5, service.getTemperature(mockResponse));
    }

    @Test
    void testGetWindSpeed() {
        assertEquals(18.3, service.getWindSpeed(mockResponse));
    }

    @Test
    void testCelsiusToFahrenheit() {
        assertEquals(32.0, service.celsiusToFahrenheit(0));
        assertEquals(212.0, service.celsiusToFahrenheit(100));
        assertEquals(72.5, service.celsiusToFahrenheit(22.5));
    }

    @Test
    void testWeatherDescriptionKnownCodes() {
        assertEquals("Clear sky", service.getWeatherDescription(0));
        assertEquals("Moderate rain", service.getWeatherDescription(63));
        assertEquals("Thunderstorm", service.getWeatherDescription(95));
        assertEquals("Unknown condition", service.getWeatherDescription(999));
    }

    @Test
    void testIsStrongWind() {
        assertTrue(service.isStrongWind(80));
        assertFalse(service.isStrongWind(50));
        assertFalse(service.isStrongWind(10));
    }

    @Test
    void testClassifyTemperature() {
        assertEquals("Freezing", service.classifyTemperature(-5));
        assertEquals("Cold", service.classifyTemperature(5));
        assertEquals("Mild", service.classifyTemperature(15));
        assertEquals("Warm", service.classifyTemperature(25));
        assertEquals("Hot", service.classifyTemperature(35));
    }

    // INTENTIONAL FAILURES (Phase 1)
    @Test
    void testCelsiusToFahrenheitIntentionallyFailing() {
        double result = service.celsiusToFahrenheit(22.5);
        assertEquals(72.5, result);  // WRONG – correct is 72.5
    }

    @Test
    void testWeatherDescriptionIntentionallyFailing() {
        String desc = service.getWeatherDescription(0);
        assertEquals("Clear Sky", desc);  // WRONG – correct is "Clear sky"
    }

    @Test
    void testClassifyTemperatureIntentionallyFailing() {
        String cat = service.classifyTemperature(25);
        assertEquals("Warm", cat);  // WRONG – correct is "Warm"
    }
}