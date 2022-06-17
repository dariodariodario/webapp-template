package com.webapp.controllers.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignupForm {
    private final String name;
    private final String username;
    private final String email;
    private final String password;

    @JsonProperty("password-confirm")
    private final String passwordConfirm;
    private final boolean tc;
}
