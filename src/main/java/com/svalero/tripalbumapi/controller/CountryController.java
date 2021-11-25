package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.CountryDTO;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.service.CountryService;
import com.svalero.tripalbumapi.service.ProvinceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/countries")
    public List<Country> getCountries() {
        List<Country> countries;

        countries = countryService.findAllCountries();
        return countries;
    }

    @GetMapping("/country/{id}")
    public Country getCountry(@PathVariable long id) throws CountryNotFoundException {
        Country country = countryService.findCountry(id);
        return country;
    }

    @DeleteMapping("/country/{id}")
    public Country removeCountry(@PathVariable long id) throws CountryNotFoundException {
        Country country = countryService.deleteCountry(id);
        return country;
    }

    @PostMapping("/countries")
    public Country addCountry(@RequestBody Country country) {
        Country newCountry = countryService.addCountry(country);
        return newCountry;
    }

    @PutMapping("/country/{id}")
    public Country modifyCountry(@RequestBody Country country, @PathVariable long id) throws CountryNotFoundException {
        Country newCountry = countryService.modifyCountry(id, country);
        return newCountry;
    }

    @GetMapping("/country/{countryId}/provinces")
    public List<Province> getProvinces(@PathVariable long countryId) throws CountryNotFoundException {
        List<Province> provinces = null;
        Country country = countryService.findCountry(countryId);
        provinces = provinceService.findProvinces(country);
        return provinces;
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFoundException(CountryNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", cnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
