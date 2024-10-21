package com.example.controller;


import com.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @PostMapping
    public Map<String, Object> getWeather(@RequestBody Map<String, String> requestData) {
        String pincode = requestData.get("pincode");
        String forDate = requestData.get("for_date");
        return weatherService.getWeatherForPincodeAndDate(pincode, forDate);
    }
}

