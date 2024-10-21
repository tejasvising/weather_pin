package com.example.service;



import com.example.model.Pincode;
import com.example.model.Weather;
import com.example.repository.PincodeRepository;
import com.example.repository.WeatherRepository;
import com.example.utils.ExternalApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private PincodeRepository pincodeRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private ExternalApiUtils externalApiUtils;

    public Map<String, Object> getWeatherForPincodeAndDate(String pincode, String forDate) {
        LocalDate date = LocalDate.parse(forDate);
        Pincode pincodeEntity = pincodeRepository.findByPincode(pincode);

        if (pincodeEntity != null) {
            // Check if weather data for the given date exists in DB
            Weather weatherEntity = weatherRepository.findByPincodeAndDate(pincodeEntity, date);
            if (weatherEntity != null) {
                return Map.of("weather_data", weatherEntity.getWeatherData());
            }
        } else {
            // Fetch lat/long using Geocoding API
            Map<String, Double> latLon = externalApiUtils.getLatLonFromPincode(pincode);
            pincodeEntity = new Pincode();
            pincodeEntity.setPincode(pincode);
            pincodeEntity.setLatitude(latLon.get("lat"));
            pincodeEntity.setLongitude(latLon.get("lon"));
            pincodeRepository.save(pincodeEntity);
        }

        // Fetch weather data using OpenWeather API
        String weatherData = externalApiUtils.getWeatherFromLatLon(pincodeEntity.getLatitude(), pincodeEntity.getLongitude(), forDate);

        // Save weather data to DB
        Weather weatherEntity = new Weather();
        weatherEntity.setPincode(pincodeEntity);
        weatherEntity.setDate(date);
        weatherEntity.setWeatherData(weatherData);
        weatherRepository.save(weatherEntity);

        return Map.of("weather_data", weatherData);
    }
}
