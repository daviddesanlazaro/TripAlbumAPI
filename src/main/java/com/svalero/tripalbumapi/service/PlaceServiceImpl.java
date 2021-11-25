package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.repository.PlaceRepository;
import com.svalero.tripalbumapi.repository.ProvinceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public List<Place> findPlaces(Province province) {
        return placeRepository.findByProvince(province);
    }

    @Override
    public Place findPlace(long id) throws PlaceNotFoundException {
        return placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
    }

    @Override
    public Place addPlace(PlaceDTO placeDto) throws ProvinceNotFoundException {
        Province province = provinceRepository.findById(placeDto.getProvince())
                .orElseThrow(ProvinceNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(placeDto, Place.class);
        place.setProvince(province);
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
    public Place modifyPlace(long id, PlaceDTO newPlaceDto) throws PlaceNotFoundException, ProvinceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
        Province province = provinceRepository.findById(id)
                .orElseThrow(ProvinceNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Place newPlace = mapper.map(newPlaceDto, Place.class);
        newPlace.setProvince(province);

        return placeRepository.save(place);
    }

}
