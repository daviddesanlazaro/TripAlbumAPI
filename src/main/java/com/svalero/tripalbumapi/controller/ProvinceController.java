package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.Province;
import com.svalero.tripalbumapi.domain.dto.ProvinceDTO;
import com.svalero.tripalbumapi.exception.CountryNotFoundException;
import com.svalero.tripalbumapi.exception.ErrorResponse;
import com.svalero.tripalbumapi.exception.ProvinceNotFoundException;
import com.svalero.tripalbumapi.service.PlaceService;
import com.svalero.tripalbumapi.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private PlaceService placeService;

    @GetMapping("/provinces")
    public List<Province> getProvinces() {
        List<Province> provinces;

        provinces = provinceService.findAllProvinces();
        return provinces;
    }

    @GetMapping("/province/{id}")
    public Province getProvince(@PathVariable long id) throws ProvinceNotFoundException {
        Province province = provinceService.findProvince(id);
        return province;
    }

    @DeleteMapping("/province/{id}")
    public Province removeProvince(@PathVariable long id) throws ProvinceNotFoundException {
        Province province = provinceService.deleteProvince(id);
        return province;
    }

    @PostMapping("/provinces")
    public Province addProvince(@RequestBody ProvinceDTO provinceDto) throws CountryNotFoundException {
        Province province = provinceService.addProvince(provinceDto);
        return province;
    }

    @PutMapping("/province/{id}")
    public Province modifyProvince(@RequestBody ProvinceDTO provinceDto, @PathVariable long id) throws ProvinceNotFoundException, CountryNotFoundException {
        Province newProvince = provinceService.modifyProvince(id, provinceDto);
        return newProvince;
    }

    @GetMapping("/province/{provinceId}/places")
    public List<Place> getPlaces(@PathVariable long provinceId) throws ProvinceNotFoundException {
        List<Place> places = null;
        Province province = provinceService.findProvince(provinceId);
        places = placeService.findPlaces(province);
        return places;
    }

    @ExceptionHandler(ProvinceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProvinceNotFoundException(ProvinceNotFoundException pnfe) {
        ErrorResponse errorResponse = new ErrorResponse("1", pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse("999", "Internal server error");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
