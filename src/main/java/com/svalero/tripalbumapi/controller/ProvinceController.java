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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;
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
    public ResponseEntity<Flux<Province>> getProvinces() {
        logger.info("Start getProvinces");
        Flux<Province> provinces;
        provinces = provinceService.findAllProvinces();
        logger.info("End getProvinces");
        return ResponseEntity.ok(provinces);
    }

    // Mostrar una provincia por ID
    @GetMapping("/province/{provinceId}")
    public ResponseEntity<Mono<Province>> getProvince(@PathVariable String provinceId) throws ProvinceNotFoundException {
        logger.info("Start ShowProvince " + provinceId);
        Mono<Province> province = provinceService.findProvince(provinceId);
        logger.info("End ShowProvince " + provinceId);
        return ResponseEntity.ok(province);
    }

    // Eliminar una provincia
    @DeleteMapping("/province/{provinceId}")
    public ResponseEntity<Mono<Void>> deleteProvince(@PathVariable String provinceId) throws ProvinceNotFoundException {
        logger.info("Start DeleteProvince " + provinceId);
        Mono<Void> mono = provinceService.deleteProvince(provinceId);
        logger.info("End DeleteProvince " + provinceId);
        return ResponseEntity.ok(mono);
    }

    // Insertar una provincia
    @PostMapping("/provinces")
    public ResponseEntity<Mono<Province>> addProvince(@Valid @RequestBody ProvinceDTO provinceDto) throws CountryNotFoundException {
        logger.info("Start AddProvince");
        Mono<Province> province = provinceService.addProvince(provinceDto);
        logger.info("End AddProvince");
        return ResponseEntity.ok(province);
    }

    // Modificar una provincia
    @PutMapping("/province/{provinceId}")
    public ResponseEntity<Mono<Province>> modifyProvince(@Valid @RequestBody ProvinceDTO provinceDto, @PathVariable String provinceId) throws ProvinceNotFoundException, CountryNotFoundException {
        logger.info("Start ModifyProvince " + provinceId);
        Mono<Province> newProvince = provinceService.modifyProvince(provinceId, provinceDto);
        logger.info("End ModifyProvince " + provinceId);
        return ResponseEntity.ok(newProvince);
    }

    // Cambiar nombre de una provincia
    @PatchMapping("/province/{provinceId}")
    public ResponseEntity<Mono<Province>> patchProvince(@PathVariable String provinceId, @Valid @RequestBody String name) throws ProvinceNotFoundException {
        logger.info("Start PatchProvince " + provinceId);
        Mono<Province> province = provinceService.patchProvince(provinceId, name);
        logger.info("End PatchProvince " + provinceId);
        return ResponseEntity.ok(province);
    }

    // Mostrar todos los lugares de una provincia
    @GetMapping("/province/{provinceId}/places")
    public ResponseEntity<Flux<Place>> getPlaces(@PathVariable String provinceId,
               @RequestParam (name = "name", required = false, defaultValue = "") String name) throws ProvinceNotFoundException {
        logger.info("Start getPlacesByProvince");
        Flux<Place> places = null;
        logger.info("Search for province " + provinceId);
        Mono<Province> province = provinceService.findProvince(provinceId);
        logger.info("Province found. Search for places");
        if (name.equals("")) {
            logger.info("Show all places");
            places = placeService.findPlaces(province.block());
        } else {
            logger.info("Show places with search");
            places = placeService.findByProvinceAndSearch(province.block(), name);
        }

        logger.info("End getPlacesByProvince");
        return ResponseEntity.ok(places);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        logger.info("400: Argument not valid");
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
        logger.info("400: Bad request");
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        logger.info("404: Province not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCountryNotFoundException(CountryNotFoundException cnfe) {
        logger.info("404: Country not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(cnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
