package com.example.AgentApplication.service;

import com.example.AgentApplication.domain.City;
import com.example.AgentApplication.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getCityLike(String city) {
        return cityRepository.findCitiesLike(city);
    }
}
