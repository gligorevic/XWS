package com.example.SearchService.service;

import com.example.SearchService.domain.City;
import com.example.SearchService.exception.CustomException;
import com.example.SearchService.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public List<City> getCityLike(String city) {
        return cityRepository.findCitiesLike(city);
    }

    public Long getCityId(String cityName) throws CustomException {
        if (cityName != null) {
            City city = cityRepository.findCityByName(cityName);
            if (city == null)
                throw new CustomException("City doesn't exist", HttpStatus.BAD_REQUEST);

            return city.getId();
        }
        return null;
    }
}
