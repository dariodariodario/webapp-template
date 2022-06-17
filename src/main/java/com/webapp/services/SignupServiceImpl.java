package com.webapp.services;

import com.webapp.controllers.model.SignupForm;
import com.webapp.model.ConfirmCode;
import com.webapp.model.User;
import com.webapp.repositories.ConfirmCodesRepository;
import com.webapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Service
public class SignupServiceImpl implements SignupService {

    private static final int BIT_LENGTH_CODE = 256;
    private static final String CONFIRM_TYPE_REGISTRATION = "CONFIRM_REGISTRATION";
    private static final Logger LOGGER = LoggerFactory.getLogger(SignupServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmCodesRepository confirmCodesRepository;

    @Override
    @Transactional
    public void saveUser(SignupForm signupForm) {

        var takenUsername = userRepository.findByUsername(signupForm.getUsername()).isPresent();
        var takenEmail = userRepository.findByEmail(signupForm.getEmail()).isPresent();

        if (takenEmail || takenUsername){
            throw new RuntimeException("email or username already in use for another user");
        }

        User newUser = new User();
        newUser.setName(signupForm.getName());
        newUser.setUsername(signupForm.getUsername());
        newUser.setEmail(signupForm.getEmail());
        newUser.setEnabled(false);
        newUser.setPassword(passwordEncoder.encode(signupForm.getPassword()));

        newUser = userRepository.save(newUser);

        Random rand = new Random();
        BigInteger result = new BigInteger(BIT_LENGTH_CODE, rand);
        String code = result.toString(16);

        var confirmCode = new ConfirmCode();
        confirmCode.setCode(code);
        confirmCode.setType(CONFIRM_TYPE_REGISTRATION);
        confirmCode.setContent(newUser.getId().toString());
        var expDate = Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
        confirmCode.setExpiration(expDate);
        confirmCodesRepository.save(confirmCode);
        LOGGER.error("link -> http://localhost:8080/signup/confirm?code=" + code);
    }

    @Override
    @Transactional
    public void confirmUser(String linkCode) {
        confirmCodesRepository.findById(linkCode)
                .ifPresentOrElse(confirmCode -> {
                    if (confirmCode.getExpiration().toInstant().isBefore(Instant.now())) {
                        throw new RuntimeException("This code is expired");
                    }

                    if (!CONFIRM_TYPE_REGISTRATION.equals(confirmCode.getType())) {
                        throw new RuntimeException("This code is not valid");
                    }

                    User user = userRepository.findById(Long.valueOf(confirmCode.getContent()))
                            .orElseThrow(() -> new RuntimeException("User not found " + confirmCode.getContent()));

                    user.setEnabled(true);
                    confirmCodesRepository.delete(confirmCode);
                    userRepository.save(user);
                    LOGGER.info("Confirmed user " + user.getUsername());
                }, () -> {
                    throw new RuntimeException("Error this confirmation code is not found");
                });
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean checkEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
