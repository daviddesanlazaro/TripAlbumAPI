package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long>  {
    List<Visit> findAll();
    List<Visit> findByUser(User user);
    List<Visit> findByPlace(Place place);
}
