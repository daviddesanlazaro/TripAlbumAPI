package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import com.svalero.tripalbumapi.domain.Visit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findBySendDataOrNameOrSurname(boolean sendData, String name, String surname);

//    @Query(value = "SELECT p FROM places p WHERE id = ANY (SELECT v.place FROM visits v WHERE v.user = 2)")
//    List<Place> findPlacesUser(long userId);
    @Query(value = "SELECT v FROM visits v WHERE user = ?1")
    List<Visit> findVisitsUser(long userId);
}
