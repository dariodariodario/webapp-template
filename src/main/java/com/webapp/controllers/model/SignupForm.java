package com.webapp.controllers.model;

import lombok.Data;

@Data
public class SignupForm {
    private final String name;
    private final String username;
    private final String email;
    private final String password;
    private final String passwordConfirm;
}
