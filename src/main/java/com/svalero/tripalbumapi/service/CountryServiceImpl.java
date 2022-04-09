package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Flux<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Mono<Country> findCountry(String id) throws CountryNotFoundException {
        return countryRepository.findById(id).onErrorReturn(new Country());
    }

    @Override
    public Mono<Country> addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Mono<Void> deleteCountry(String id) throws CountryNotFoundException {
        Mono<Country> country = countryRepository.findById(id).onErrorReturn(new Country());
        return countryRepository.deleteById(id);
    }

    @Override
    public Mono<Country> modifyCountry(String id, Country newCountry) throws CountryNotFoundException {
        Mono<Country> monoCountry = countryRepository.findById(id).onErrorReturn(new Country());
        Country country = monoCountry.block();
        country.setName(newCountry.getName());
        return countryRepository.save(country);
    }
}
