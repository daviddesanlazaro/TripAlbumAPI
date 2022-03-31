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
    List<User> findByNameOrSurname(String name, String surname);

    // Mostrar los lugares que ha visitado un usuario
    @Query("SELECT p FROM Place p WHERE id = ANY (SELECT v.place FROM Visit v WHERE v.user = ?1)")
    List<Place> findPlacesUser(User user);

    // Mostrar los lugares favoritos de un usuario
    @Query("SELECT p FROM Place p WHERE id = ANY (SELECT f.place FROM Favorite f WHERE f.user = ?1)")
    List<Place> findFavoritePlacesUser(User user);

    // Mostrar los amigos de un usuario
    @Query("SELECT u FROM User u WHERE id = ANY (SELECT f.friend FROM Friendship f WHERE f.user = ?1)")
    List<User> findFriendsUser(User user);
}
