package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.repository.PlaceRepository;
import com.svalero.tripalbumapi.repository.ProvinceRepository;
import com.svalero.tripalbumapi.repository.UserRepository;
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
    @Autowired
    private UserRepository userRepository;

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
        Province province = provinceRepository.findById(newPlaceDto.getProvince())
                .orElseThrow(ProvinceNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        place = mapper.map(newPlaceDto, Place.class);
        place.setId(id);
        place.setProvince(province);

        return placeRepository.save(place);
    }

    @Override
    public Place patchPlace(long id, String description) throws PlaceNotFoundException {
        Place place = placeRepository.findById(id)
                .orElseThrow(PlaceNotFoundException::new);
        place.setDescription(description);
        return placeRepository.save(place);
    }

    @Override
    public float averageRating(long placeId) throws PlaceNotFoundException {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
        return placeRepository.averageRating(placeId);
    }

    @Override
    public int numVisits(long placeId) throws PlaceNotFoundException {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
        return placeRepository.numVisits(placeId);
    }

    @Override
    public int numUsers(long placeId) throws PlaceNotFoundException {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(PlaceNotFoundException::new);
        return placeRepository.numUsers(placeId);
    }
}
