package com.webapp.repositories;

import com.webapp.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<UserDetails> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
