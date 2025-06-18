package com.jeferson.springcloud.msvc.users.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.jeferson.springcloud.msvc.users.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
   Optional<User> findByUsername (String username); 
}