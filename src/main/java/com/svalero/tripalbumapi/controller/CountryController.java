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

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<?> getCountries() {
        logger.info("Start getCountries");
        List<Country> countries;
        countries = countryService.findAllCountries();
        logger.info("End getCountries");
        return ResponseEntity.ok(countries);
    }

    // Mostrar un país por ID
    @GetMapping("/country/{id}")
    public ResponseEntity<?> getCountry(@PathVariable long id) throws CountryNotFoundException {
        logger.info("Start ShowCountry " + id);
        Country country = countryService.findCountry(id);
        logger.info("End ShowCountry " + id);
        return ResponseEntity.ok(country);
    }

    // Eliminar un país
    @DeleteMapping("/country/{id}")
    public void removeCountry(@PathVariable long id) throws CountryNotFoundException {
        logger.info("Start DeleteCountry " + id);
        countryService.deleteCountry(id);
        logger.info("End DeleteCountry " + id);;
    }

    // Insertar un país
    @PostMapping("/countries")
    public ResponseEntity<?> addCountry(@Valid @RequestBody Country country) {
        logger.info("Start AddCountry");

//        if (country.getName() == null)
//            return ResponseEntity.badRequest().body(ErrorResponse.badRequest("El nombre no es correcto"));
//        logger.info("Request accepted");

        Country newCountry = countryService.addCountry(country);
        logger.info("End AddCountry");
        return ResponseEntity.ok(newCountry);
    }

    // Modificar un país
    @PutMapping("/country/{id}")
    public ResponseEntity<?> modifyCountry(@Valid @RequestBody Country country, @PathVariable long id) throws CountryNotFoundException {
        logger.info("Start ModifyCountry " + id);

//        if (country.getName() == null)
//            return ResponseEntity.badRequest().body(ErrorResponse.badRequest("El nombre no es correcto"));
//        logger.info("Request accepted");

        Country newCountry = countryService.modifyCountry(id, country);
        logger.info("End ModifyCountry " + id);
        return ResponseEntity.ok(newCountry);
    }

    // Mostrar todas las provincias de un país
    @GetMapping("/country/{countryId}/provinces")
    public ResponseEntity<?> getProvinces(@PathVariable long countryId) throws CountryNotFoundException {
        logger.info("Start getProvincesByCountry");
        List<Province> provinces = null;
        logger.info("Search for country " + countryId);
        Country country = countryService.findCountry(countryId);
        logger.info("Country found. Search for provinces");
        provinces = provinceService.findProvinces(country);
        logger.info("End getProvincesByCountry");
        return ResponseEntity.ok(provinces);
    }

//    // Cambiar el nombre de un país
//    @PatchMapping("/country/{id}")
//    public Country patchCountry(@PathVariable long id, @RequestBody String name) throws CountryNotFoundException {
//        logger.info("Start PatchCountry " + id);
//        Country country = countryService.patchCountry(id, name);
//        logger.info("End patchCountry " + id);
//        return country;
//    }

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
