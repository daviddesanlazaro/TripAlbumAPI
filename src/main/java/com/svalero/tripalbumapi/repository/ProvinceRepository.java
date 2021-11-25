package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Country;
import com.svalero.tripalbumapi.domain.Province;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends CrudRepository<Province, Long>  {
    List<Province> findAll();
    List<Province> findByCountry(Country country);
}
