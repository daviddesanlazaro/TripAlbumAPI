package com.svalero.tripalbumapi.repository;

import com.svalero.tripalbumapi.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    List<User> findBySendDataOrNameOrSurname(boolean sendData, String name, String surname);
}
