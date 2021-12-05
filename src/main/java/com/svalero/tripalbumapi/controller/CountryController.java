package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.service.CountryService;
import com.svalero.tripalbumapi.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/countries")
    public List<Country> getCountries() {
        logger.info("Start getCountries");
        List<Country> countries;
        countries = countryService.findAllCountries();
        logger.info("End getCountries");
        return countries;
    }

    @GetMapping("/country/{id}")
    public Country getCountry(@PathVariable long id) throws CountryNotFoundException {
        logger.info("Start ShowCountry " + id);
        Country country = countryService.findCountry(id);
        logger.info("End ShowCountry " + id);
        return country;
    }

    @DeleteMapping("/country/{id}")
    public Country removeCountry(@PathVariable long id) throws CountryNotFoundException {
        logger.info("Start DeleteCountry " + id);
        Country country = countryService.deleteCountry(id);
        logger.info("End DeleteCountry " + id);
        return country;
    }

    @PostMapping("/countries")
    public Country addCountry(@RequestBody Country country) {
        logger.info("Start AddCountry");
        Country newCountry = countryService.addCountry(country);
        logger.info("End AddCountry");
        return newCountry;
    }

    @PutMapping("/country/{id}")
    public Country modifyCountry(@RequestBody Country country, @PathVariable long id) throws CountryNotFoundException {
        logger.info("Start ModifyCountry " + id);
        Country newCountry = countryService.modifyCountry(id, country);
        logger.info("End ModifyCountry " + id);
        return newCountry;
    }

    @GetMapping("/country/{countryId}/provinces")
    public List<Province> getProvinces(@PathVariable long countryId) throws CountryNotFoundException {
        logger.info("Start getProvincesByCountry");
        List<Province> provinces = null;
        logger.info("Search for country " + countryId);
        Country country = countryService.findCountry(countryId);
        logger.info("Country found. Search for provinces");
        provinces = provinceService.findProvinces(country);
        logger.info("End getProvincesByCountry");
        return provinces;
    }

    @PatchMapping("/country/{id}")
    public Country patchCountry(@PathVariable long id, @RequestBody String name) throws CountryNotFoundException {
        logger.info("Start PatchCountry " + id);
        Country country = countryService.patchCountry(id, name);
        logger.info("End patchCountry " + id);
        return country;
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFoundException(CountryNotFoundException cnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", cnfe.getMessage());
        logger.info(cnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
