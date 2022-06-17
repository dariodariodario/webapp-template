package com.webapp.services;

import com.webapp.model.User;
import com.webapp.repositories.UserRepository;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public
class DevelopmentDataBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevelopmentDataBuilder.class);
    public static final String DEV_SETUP = "DEV_SETUP";

    @Autowired
    AdminJobService adminJobService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (!adminJobService.isJobDone(DEV_SETUP)) {
            var job = adminJobService.startAdminJobName(DEV_SETUP);
            LOGGER.info("Inserting dev data into db");
            insert();
            LOGGER.info("Done");
            adminJobService.complete(job);
        } else {
            LOGGER.info("Dev data already done.");
        }
    }


    @Transactional
    protected void insert() {

        // create and add some users


        var mainUser = User.builder()
                .username("mainuser")
                .password(passwordEncoder.encode("test"))
                .name("Jon")
                .enabled(true)
                .build();

        userRepository.save(mainUser);

        // todo add more development setup
    }
}
