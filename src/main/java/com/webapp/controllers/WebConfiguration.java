package com.webapp.controllers;

import com.mitchellbosecke.pebble.loader.FileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {
    @Bean
    public FileLoader pebbleLoader() {
        return new FileLoader();
    }

}
