package com.webapp.model.service;

import lombok.Data;

import java.util.Optional;

@Data
public class GenericResult<T> {
    private final Long id;
    private final Optional<T> content;
}
