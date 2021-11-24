package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;

import java.util.List;

public interface PlaceService {
    List<Place> findAllPlaces();
    List<Place> findAllPlaces(long provinceId);
    Place findPlace(long id) throws PlaceNotFoundException;

    Place addPlace(Place place);
    Place deletePlace(long id) throws PlaceNotFoundException;
    Place modifyPlace(long id, Place place) throws PlaceNotFoundException;
}
