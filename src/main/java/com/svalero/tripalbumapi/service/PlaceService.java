package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;

import java.util.List;

public interface PlaceService {
    List<Place> findAllPlaces();
    List<Place> findPlaces(Province province);
    Place findPlace(long id) throws PlaceNotFoundException;

    Place addPlace(PlaceDTO placeDto) throws ProvinceNotFoundException;
    void deletePlace(long id) throws PlaceNotFoundException;
    Place modifyPlace(long id, PlaceDTO placeDto) throws PlaceNotFoundException, ProvinceNotFoundException;
    Place patchPlace(long id, String description) throws PlaceNotFoundException;

    float averageRating(long placeId) throws PlaceNotFoundException;
    int numVisits(long placeId) throws PlaceNotFoundException;
//    int numUsers(long placeId) throws PlaceNotFoundException;

//    List<Place> findByProvinceLatitude(PlaceDTO placeDto) throws ProvinceNotFoundException;
}
