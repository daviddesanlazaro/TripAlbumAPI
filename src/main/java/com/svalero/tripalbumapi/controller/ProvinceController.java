package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.service.PlaceService;
import com.svalero.tripalbumapi.service.ProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProvinceController {

    private final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private PlaceService placeService;

    // Mostrar todas las provincias
    @GetMapping("/provinces")
    public List<Province> getProvinces() {
        logger.info("Start getProvinces");
        List<Province> provinces;

        provinces = provinceService.findAllProvinces();
        logger.info("End getProvinces");
        return provinces;
    }

    // Mostrar una provincia por ID
    @GetMapping("/province/{id}")
    public Province getProvince(@PathVariable long id) throws ProvinceNotFoundException {
        logger.info("Start ShowProvince " + id);
        Province province = provinceService.findProvince(id);
        logger.info("End ShowProvince " + id);
        return province;
    }

    // Eliminar una provincia
    @DeleteMapping("/province/{id}")
    public Province removeProvince(@PathVariable long id) throws ProvinceNotFoundException {
        logger.info("Start DeleteProvince " + id);
        Province province = provinceService.deleteProvince(id);
        logger.info("End DeleteProvince " + id);
        return province;
    }

    // Insertar una provincia
    @PostMapping("/provinces")
    public Province addProvince(@RequestBody ProvinceDTO provinceDto) throws CountryNotFoundException {
        logger.info("Start AddProvince");
        Province province = provinceService.addProvince(provinceDto);
        logger.info("End AddProvince");
        return province;
    }

    // Modificar una provincia
    @PutMapping("/province/{id}")
    public Province modifyProvince(@RequestBody ProvinceDTO provinceDto, @PathVariable long id) throws ProvinceNotFoundException, CountryNotFoundException {
        logger.info("Start ModifyProvince " + id);
        Province newProvince = provinceService.modifyProvince(id, provinceDto);
        logger.info("End ModifyProvince " + id);
        return newProvince;
    }

    // Mostrar todos los lugares de una provincia
    @GetMapping("/province/{provinceId}/places")
    public List<Place> getPlaces(@PathVariable long provinceId) throws ProvinceNotFoundException {
        logger.info("Start getPlacesByProvince");
        List<Place> places = null;
        logger.info("Search for province " + provinceId);
        Province province = provinceService.findProvince(provinceId);
        logger.info("Province found. Search for places");
        places = placeService.findPlaces(province);
        logger.info("End getPlacesByProvince");
        return places;
    }

    // Cambiar el nombre de una provincia
    @PatchMapping("/province/{id}")
    public Province patchProvince(@PathVariable long id, @RequestBody String name) throws ProvinceNotFoundException {
        logger.info("Start PatchProvince " + id);
        Province province = provinceService.patchProvince(id, name);
        logger.info("End patchProvince " + id);
        return province;
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        logger.info(pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
