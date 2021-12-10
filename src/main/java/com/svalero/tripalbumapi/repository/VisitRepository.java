package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import com.svalero.tripalbumapi.exception.VisitNotFoundException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long>  {
    List<Visit> findAll();
    List<Visit> findByUser(User user);
    List<Visit> findByPlace(Place place);

    @Query(value = "SELECT v FROM visits v WHERE date >= :date")
    List<Visit> findRecentVisits(@Param("date") LocalDate localDate);

    @Query(value = "SELECT commentary FROM visits v WHERE id = ?1")
    String findCommentary(long id) throws VisitNotFoundException;
}
