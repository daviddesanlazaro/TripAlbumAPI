package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
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
        return placeRepository.findAll();
    }

    @Override
    public List<Place> findAllPlaces(long provinceId) {
        return placeRepository.findByProvinceId(provinceId);
    }

    @Override
    public Place findPlace(long id) throws PlaceNotFoundException {
        return placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public Place addPlace(Place place) {
        return placeRepository.save(place);
    }

    @Override
    public Place deletePlace(long id) throws PlaceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
        placeRepository.delete(place);
        return place;
    }

    @Override
    public Place modifyPlace(long id, Place newPlace) throws PlaceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
        place.setName(newPlace.getName());
        place.setDescription(newPlace.getDescription());
        place.setLatitude(newPlace.getLatitude());
        place.setLongitude(newPlace.getLongitude());
        place.setProvinceId(newPlace.getProvinceId());

        return placeRepository.save(place);
    }

}
