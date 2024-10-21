package com.example.repository;


import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Pincode;
import com.example.model.Weather;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Weather findByPincodeAndDate(Pincode pincode, LocalDate date);
}
