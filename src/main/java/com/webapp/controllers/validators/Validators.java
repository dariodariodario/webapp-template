package com.webapp.controllers.validators;

import com.webapp.controllers.model.SignupForm;
import com.webapp.util.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

@Component
public class Validators {

    public static boolean isValidSignupForm(SignupForm signupForm) {
        var allPresent = !StringUtils.isEmpty(signupForm.getName())
                && !StringUtils.isEmpty(signupForm.getUsername())
                && !StringUtils.isEmpty(signupForm.getEmail())
                && !StringUtils.isEmpty(signupForm.getPassword())
                && !StringUtils.isEmpty(signupForm.getPasswordConfirm())
                && signupForm.isTc();
        var emailValid = EmailValidator.getInstance().isValid(signupForm.getEmail());
        var passwordMatch = signupForm.getPassword().equals(signupForm.getPasswordConfirm());
        return allPresent
                && emailValid
                && passwordMatch;
    }

}
