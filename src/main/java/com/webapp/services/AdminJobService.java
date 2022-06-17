package com.webapp.services;

import com.webapp.model.AdminJob;
import com.webapp.repositories.AdminJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.String.format;

@Service
public class AdminJobService {


    @Autowired
    private AdminJobRepository adminJobRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AdminJob startAdminJobName(String name) {
        var existsJob = getJobInProgressMaybe(name);
        if (existsJob.isPresent()) {
            throw new IllegalStateException(format("A job is in progress %s", existsJob.get()));
        }
        var job = AdminJob.builder().name(name).expiresSeconds(30).build();
        return adminJobRepository.save(job);
    }

    public boolean isJobDone(String name) {
        return adminJobRepository.getByName(name).stream().filter(AdminJob::isComplete).findAny().isPresent();
    }

    private Optional<AdminJob> getJobInProgressMaybe(String name) {
        return adminJobRepository.getByName(name).stream().filter(job -> !job.isComplete())
                .filter(Predicate.not(AdminJob::isExpired))
                .findAny();
    }

    public void complete(AdminJob job) {
        job.setComplete(true);
        adminJobRepository.save(job);
    }
}
