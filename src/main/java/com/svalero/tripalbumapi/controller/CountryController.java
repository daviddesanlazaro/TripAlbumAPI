package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.exception.BadRequestException;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.InternalServerErrorException;
import com.svalero.tripalbumapi.service.CountryService;
import com.svalero.tripalbumapi.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CountryController {

    private final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;
    @Autowired
    private ProvinceService provinceService;

    // Mostrar todos los países
    @GetMapping("/countries")
    public ResponseEntity<Flux<Country>> getCountries() {
        logger.info("Start getCountries");
        Flux<Country> countries;
        countries = countryService.findAllCountries();
        logger.info("End getCountries");
        return ResponseEntity.ok(countries);
    }

    // Mostrar un país por ID
    @GetMapping("/country/{countryId}")
    public ResponseEntity<Mono<Country>> getCountry(@PathVariable String countryId) throws CountryNotFoundException {
        logger.info("Start ShowCountry " + countryId);
        Mono<Country> country = countryService.findCountry(countryId);
        logger.info("End ShowCountry " + countryId);
        return ResponseEntity.ok(country);
    }

    // Eliminar un país
    @DeleteMapping("/country/{countryId}")
    public ResponseEntity<Mono<Void>> deleteCountry(@PathVariable String countryId) throws CountryNotFoundException {
        logger.info("Start DeleteCountry " + countryId);
        Mono<Void> mono = countryService.deleteCountry(countryId);
        logger.info("End DeleteCountry " + countryId);
        return ResponseEntity.ok(mono);
    }

    // Insertar un país
    @PostMapping("/countries")
    public ResponseEntity<Mono<Country>> addCountry(@Valid @RequestBody Country country) {
        logger.info("Start AddCountry");
        Mono<Country> newCountry = countryService.addCountry(country);
        logger.info("End AddCountry");
        return ResponseEntity.ok(newCountry);
    }

    // Modificar un país
    @PutMapping("/country/{countryId}")
    public ResponseEntity<Mono<Country>> modifyCountry(@Valid @RequestBody Country country, @PathVariable String countryId) throws CountryNotFoundException {
        logger.info("Start ModifyCountry " + countryId);
        Mono<Country> newCountry = countryService.modifyCountry(countryId, country);
        logger.info("End ModifyCountry " + countryId);
        return ResponseEntity.ok(newCountry);
    }

    // Mostrar todas las provincias de un país
    @GetMapping("/country/{countryId}/provinces")
    public ResponseEntity<Flux<Province>> getProvinces(@PathVariable String countryId) throws CountryNotFoundException {
        logger.info("Start getProvincesByCountry");
        Flux<Province> provinces = null;
        logger.info("Search for country " + countryId);
        Mono<Country> country = countryService.findCountry(countryId);
        logger.info("Country found. Search for provinces");
        provinces = provinceService.findProvinces(country.block());
        logger.info("End getProvincesByCountry");
        return ResponseEntity.ok(provinces);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFoundException(CountryNotFoundException cnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(cnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        return ResponseEntity.badRequest().body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
