package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Place;
import com.svalero.tripalbumapi.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findBySendDataOrNameOrSurname(boolean sendData, String name, String surname);

    // Mostrar los lugares que ha visitado un usuario
    @Query("SELECT p FROM Place p WHERE id = ANY (SELECT v.place FROM Visit v WHERE v.user = ?1)")
    List<Place> findPlacesUser(User user);
}
