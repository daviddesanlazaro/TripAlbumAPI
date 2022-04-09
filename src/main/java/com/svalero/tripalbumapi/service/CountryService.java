package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryService {
    Flux<Country> findAllCountries();
    Mono<Country> findCountry(String id) throws CountryNotFoundException;

    Mono<Country> addCountry(Country country);
    Mono<Void> deleteCountry(String id) throws CountryNotFoundException;
    Mono<Country> modifyCountry(String id, Country country) throws CountryNotFoundException;
}
