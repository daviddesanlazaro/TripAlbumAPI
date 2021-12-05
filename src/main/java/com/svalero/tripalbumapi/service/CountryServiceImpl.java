package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country findCountry(long id) throws CountryNotFoundException {
        return countryRepository.findById(id)
                .orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country deleteCountry(long id) throws CountryNotFoundException {
        Country country = countryRepository.findById(id)
                .orElseThrow(CountryNotFoundException::new);
        countryRepository.delete(country);
        return country;
    }

    @Override
    public Country modifyCountry(long id, Country newCountry) throws CountryNotFoundException {
        Country country = countryRepository.findById(id)
                .orElseThrow(CountryNotFoundException::new);
        country.setName(newCountry.getName());

        return countryRepository.save(country);
    }

    @Override
    public Country patchCountry(long id, String name) throws CountryNotFoundException {
        Country country = countryRepository.findById(id)
                .orElseThrow(CountryNotFoundException::new);
        country.setName(name);
        return countryRepository.save(country);
    }
}
