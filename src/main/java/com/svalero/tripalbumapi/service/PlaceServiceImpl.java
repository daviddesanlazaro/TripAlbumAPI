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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<Place> findAllPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public Flux<Place> findPlaces(Province province) {
        return placeRepository.findByProvince(province);
    }

    @Override
    public Mono<Place> findPlace(String id) throws PlaceNotFoundException {
        return placeRepository.findById(id).onErrorReturn(new Place());
    }

    @Override
    public Mono<Place> addPlace(PlaceDTO placeDto) throws ProvinceNotFoundException {
        Mono<Province> province = provinceRepository.findById(placeDto.getProvince()).onErrorReturn(new Province());
        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(placeDto, Place.class);
        place.setProvince(province.block());
        return placeRepository.save(place);
    }

    @Override
    public Mono<Void> deletePlace(String id) throws PlaceNotFoundException {
        Mono<Place> place = placeRepository.findById(id).onErrorReturn(new Place());
        return placeRepository.deleteById(id);
    }

    @Override
    public Mono<Place> modifyPlace(String id, PlaceDTO newPlaceDto) throws PlaceNotFoundException, ProvinceNotFoundException {
        Mono<Place> monoPlace = placeRepository.findById(id).onErrorReturn(new Place());
        Mono<Province> province = provinceRepository.findById(newPlaceDto.getProvince()).onErrorReturn(new Province());

        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(newPlaceDto, Place.class);
        place.setId(id);
        place.setProvince(province.block());

        return placeRepository.save(place);
    }

    @Override
    public Mono<Place> patchPlace(String id, String description) throws PlaceNotFoundException {
        Mono<Place> monoPlace = placeRepository.findById(id).onErrorReturn(new Place());
        Place place = monoPlace.block();
        place.setDescription(description);
        return placeRepository.save(place);
    }

    @Override
    public Flux<Place> findBySearch(String name) {
        return placeRepository.findByNameContains(name);
    }

    @Override
    public Flux<Place> findByProvinceAndSearch(Province province, String name) {
        return placeRepository.findByProvinceAndNameContains(province, name);
    }
}
