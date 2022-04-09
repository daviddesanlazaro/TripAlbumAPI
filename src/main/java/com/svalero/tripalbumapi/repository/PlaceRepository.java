package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlaceRepository extends ReactiveMongoRepository<Place, String> {

    Flux<Place> findAll();
    Flux<Place> findByProvince(Province province);

    // Búsqueda por nombre
    Flux<Place> findByNameContains(String name);

    // Búsqueda por nombre y provincia
    Flux<Place> findByProvinceAndNameContains(Province province, String name);
}
