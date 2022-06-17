package com.webapp.services;

import com.webapp.controllers.model.SignupForm;

public interface SignupService {

    void saveUser(SignupForm signupForm);
    void confirmUser(String linkCode);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);
}
