package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public List<Place> findAllPlaces() {
        return null;
    }

    @Override
    public List<Place> findAllPlaces(int provinceId) {
        return placeRepository.findByProvinceId(provinceId);
    }

    @Override
    public Place findPlace(int id) {
        return placeRepository.findById(id);
    }
}
