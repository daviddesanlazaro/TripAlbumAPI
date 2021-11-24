package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

    List<Place> findAll();
    List<Place> findByProvinceId(long provinceId);
}
