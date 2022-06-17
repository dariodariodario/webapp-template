package com.webapp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StaticDataBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticDataBuilder.class);

    private static final String STATIC_DATA = "STATIC_DATA";

    @Autowired
    AdminJobService adminJobService;



    @PostConstruct
    public void run() {
        if (!adminJobService.isJobDone(STATIC_DATA)) {
            var job = adminJobService.startAdminJobName(STATIC_DATA);
            LOGGER.info("Inserting static data into db");
            insert();
            LOGGER.info("Done");
            adminJobService.complete(job);
        } else {
            LOGGER.info("Dev data already done.");
        }
    }

    private void insert() {

    }

}
