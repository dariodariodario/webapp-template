package com.webapp.repositories;

import com.webapp.model.AdminJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminJobRepository extends JpaRepository<AdminJob, Long> {
    List<AdminJob> getByName(String somejob);
    List<AdminJob> getByNameAndComplete(String somejob, boolean complete);
}
