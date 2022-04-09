package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProvinceRepository extends ReactiveMongoRepository<Province, String> {
    Flux<Province> findAll();
    Flux<Province> findByCountry(Country country);
}
