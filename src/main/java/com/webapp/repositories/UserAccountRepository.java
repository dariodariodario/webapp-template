package com.webapp.repositories;

import com.webapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountRepository extends CrudRepository<User, Long> {
}
