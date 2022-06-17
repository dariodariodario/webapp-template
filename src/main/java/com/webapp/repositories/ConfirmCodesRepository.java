package com.webapp.repositories;

import com.webapp.model.ConfirmCode;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmCodesRepository extends CrudRepository<ConfirmCode, String> {
}
