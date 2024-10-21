package com.example.utils;




import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ExternalApiUtils {

    // URLs for the Geocoding API and the Weather API from OpenWeather
    private static final String GEOCODING_API_URL = "https://api.openweathermap.org/geo/1.0/zip";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/onecall";
    
    // OpenWeather API key (ensure to add this in your environment or configuration)
    private static final String API_KEY = "YOUR_OPENWEATHER_API_KEY";

    // Using RestTemplate to make HTTP requests
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Get latitude and longitude for a given pincode using OpenWeather Geocoding API.
     *
     * @param pincode The pincode to convert to latitude and longitude.
     * @return A map containing the latitude and longitude.
     */
    public Map<String, Double> getLatLonFromPincode(String pincode) {
        String url = GEOCODING_API_URL + "?zip=" + pincode + "&appid=" + API_KEY;
        
        // Response format: {"lat": ..., "lon": ...}
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Extract latitude and longitude from the API response
        Map<String, Double> latLon = new HashMap<>();
        if (response != null) {
            latLon.put("lat", (Double) response.get("lat"));
            latLon.put("lon", (Double) response.get("lon"));
        }
        return latLon;
    }

    /**
     * Get weather data for a given latitude, longitude, and date using OpenWeather One Call API.
     *
     * @param lat     The latitude of the location.
     * @param lon     The longitude of the location.
     * @param date    The date for which to fetch the weather data.
     * @return The weather data as a JSON string.
     */
    public String getWeatherFromLatLon(double lat, double lon, String date) {
        // URL to fetch weather data for the specific location (lat/lon)
        String url = WEATHER_API_URL + "?lat=" + lat + "&lon=" + lon + "&appid=" + API_KEY + "&units=metric";

        // Note: OpenWeather One Call API does not support a specific date in the free plan,
        // so we're fetching the current weather for simplicity.
        // If needed, a historical weather API could be used to get past weather data.
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Convert the response to a JSON string (could also be stored as a structured map)
        return response != null ? response.toString() : "{}";
    }
}
