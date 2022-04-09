package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Country;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CountryRepository extends ReactiveMongoRepository<Country, String> {
    Flux<Country> findAll();
}
