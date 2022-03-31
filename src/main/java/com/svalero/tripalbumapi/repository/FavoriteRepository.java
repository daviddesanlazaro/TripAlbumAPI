package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.Favorite;
import com.svalero.tripalbumapi.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
    List<Favorite> findAll();
    List<Favorite> findByUser(User user);
}
