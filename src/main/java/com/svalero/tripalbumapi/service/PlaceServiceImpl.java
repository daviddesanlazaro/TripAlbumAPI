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
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        return placeRepository.findById(id).switchIfEmpty(placeError);
    }

    @Override
    public Mono<Place> addPlace(PlaceDTO placeDto) throws ProvinceNotFoundException {
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        Mono<Province> monoProvince = provinceRepository.findById(placeDto.getProvince()).switchIfEmpty(provinceError);
        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(placeDto, Place.class);
        place.setProvince(monoProvince.block());
        return placeRepository.save(place);
    }

    @Override
    public Mono<Void> deletePlace(String id) throws PlaceNotFoundException {
        Mono<Place> place = Mono.error(new PlaceNotFoundException());
        placeRepository.findById(id).switchIfEmpty(place);
        return placeRepository.deleteById(id);
    }

    @Override
    public Mono<Place> modifyPlace(String id, PlaceDTO placeDto) throws PlaceNotFoundException, ProvinceNotFoundException {
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        Mono<Place> monoPlace = placeRepository.findById(id).switchIfEmpty(placeError);
        Mono<Province> provinceError = Mono.error(new ProvinceNotFoundException());
        Mono<Province> monoProvince = provinceRepository.findById(placeDto.getProvince()).switchIfEmpty(provinceError);

        if (!monoPlace.block().getId().equals(id))
            return monoPlace;

        ModelMapper mapper = new ModelMapper();
        Place place = mapper.map(placeDto, Place.class);
        place.setId(id);
        place.setProvince(monoProvince.block());

        return placeRepository.save(place);
    }

    @Override
    public Mono<Place> patchPlace(String id, String description) throws PlaceNotFoundException {
        Mono<Place> placeError = Mono.error(new PlaceNotFoundException());
        Place place = placeRepository.findById(id).switchIfEmpty(placeError).block();
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
