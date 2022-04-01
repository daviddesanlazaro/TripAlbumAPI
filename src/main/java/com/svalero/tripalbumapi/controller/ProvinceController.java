package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.*;
import com.svalero.tripalbumapi.service.PlaceService;
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
public class ProvinceController {

    private final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private PlaceService placeService;

    // Mostrar todas las provincias
    @GetMapping("/provinces")
    public ResponseEntity<?> getProvinces() {
        logger.info("Start getProvinces");
        List<Province> provinces;

        provinces = provinceService.findAllProvinces();
        logger.info("End getProvinces");
        return ResponseEntity.ok(provinces);
    }

    // Mostrar una provincia por ID
    @GetMapping("/province/{id}")
    public ResponseEntity<?> getProvince(@PathVariable long id) throws ProvinceNotFoundException {
        logger.info("Start ShowProvince " + id);
        Province province = provinceService.findProvince(id);
        logger.info("End ShowProvince " + id);
        return ResponseEntity.ok(province);
    }

    // Eliminar una provincia
    @DeleteMapping("/province/{id}")
    public void removeProvince(@PathVariable long id) throws ProvinceNotFoundException {
        logger.info("Start DeleteProvince " + id);
        provinceService.deleteProvince(id);
        logger.info("End DeleteProvince " + id);
    }

    // Insertar una provincia
    @PostMapping("/provinces")
    public ResponseEntity<?> addProvince(@Valid @RequestBody ProvinceDTO provinceDto) throws CountryNotFoundException {
        logger.info("Start AddProvince");
        Province province = provinceService.addProvince(provinceDto);
        logger.info("End AddProvince");
        return ResponseEntity.ok(province);
    }

    // Modificar una provincia
    @PutMapping("/province/{id}")
    public ResponseEntity<?> modifyProvince(@Valid @RequestBody ProvinceDTO provinceDto, @PathVariable long id) throws ProvinceNotFoundException, CountryNotFoundException {
        logger.info("Start ModifyProvince " + id);
        Province newProvince = provinceService.modifyProvince(id, provinceDto);
        logger.info("End ModifyProvince " + id);
        return ResponseEntity.ok(newProvince);
    }

    // Mostrar todos los lugares de una provincia
    @GetMapping("/province/{provinceId}/places")
    public ResponseEntity<?> getPlaces(@PathVariable long provinceId,
               @RequestParam (name = "name", required = false, defaultValue = "") String name) throws ProvinceNotFoundException {
        logger.info("Start getPlacesByProvince");
        List<Place> places = null;
        logger.info("Search for province " + provinceId);
        Province province = provinceService.findProvince(provinceId);
        logger.info("Province found. Search for places");
        if (name.equals("")) {
            logger.info("Show all places");
            places = placeService.findPlaces(province);
        } else {
            logger.info("Show places with search");
            places = placeService.findByProvinceAndSearch(province, name);
        }

        logger.info("End getPlacesByProvince");
        return ResponseEntity.ok(places);
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

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        return ResponseEntity.badRequest().body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
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
