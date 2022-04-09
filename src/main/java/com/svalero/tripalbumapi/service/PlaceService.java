package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.PlaceDTO;
import com.svalero.tripalbumapi.exception.PlaceNotFoundException;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaceService {
    Flux<Place> findAllPlaces();
    Flux<Place> findPlaces(Province province);
    Mono<Place> findPlace(String id) throws PlaceNotFoundException;

    Mono<Place> addPlace(PlaceDTO placeDto) throws ProvinceNotFoundException;
    Mono<Void> deletePlace(String id) throws PlaceNotFoundException;
    Mono<Place> modifyPlace(String id, PlaceDTO placeDto) throws PlaceNotFoundException, ProvinceNotFoundException;
    Mono<Place> patchPlace(String id, String description) throws PlaceNotFoundException;

    Flux<Place> findBySearch(String name);
    Flux<Place> findByProvinceAndSearch(Province province, String name);
}
