package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long>  {
    List<Country> findAll();
}
