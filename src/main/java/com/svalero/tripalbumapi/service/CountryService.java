package com.svalero.tripalbumapi.service;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;

import java.util.List;

public interface CountryService {
    List<Country> findAllCountries();
    Country findCountry(long id) throws CountryNotFoundException;

    Country addCountry(Country country);
    Country deleteCountry(long id) throws CountryNotFoundException;
    Country modifyCountry(long id, Country country) throws CountryNotFoundException;
    Country patchCountry(long id, String name) throws CountryNotFoundException;

}
