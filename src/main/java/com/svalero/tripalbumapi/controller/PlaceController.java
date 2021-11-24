package com.svalero.tripalbumapi.controller;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @GetMapping("/places")
    public List<Place> getPlacesByProvinceId(int provinceId) {
        List<Place> places = placeService.findAllPlaces(provinceId);
        return places;
    }

    @GetMapping("place/{id}")
    public Place getPlace(@PathVariable int id) {
        Place place = placeService.findPlace(id);
        return place;
    }
}
