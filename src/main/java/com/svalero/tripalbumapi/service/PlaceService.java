package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findAllPlaces();
    List<Place> findAllPlaces(int provinceId);
    Place findPlace(int id);
}
