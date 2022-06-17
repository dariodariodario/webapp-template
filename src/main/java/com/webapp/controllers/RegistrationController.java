package com.webapp.controllers;

import com.webapp.controllers.model.SignupForm;
import com.webapp.controllers.traits.WithStdData;
import com.webapp.controllers.validators.Validators;
import com.webapp.services.SignupService;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class RegistrationController implements WithStdData {

    @Autowired
    SignupService signupService;

    @Autowired
    ViewResolver viewResolver;

    @RequestMapping("")
    public String signupMain() {
        return "registration/signup.html";
    }


    //TODO security may be abused
    @RequestMapping("/check-registration-details")
    public ResponseEntity<Map<String, Object>> checkRegistrationDetailsJson(@RequestParam Map<String, String> details) {
        if (details.containsKey("email") && details.containsKey("username")) {
            var existsUsername = signupService.checkUsernameExists(details.get("username"));
            var existsEmail = signupService.checkEmailExists(details.get("email"));
            return ResponseEntity.ok(Map.of(
                    "existsEmail", existsEmail,
                    "existsUsername", existsUsername
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "wrong json object in request"));
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signupSubmit(SignupForm signupForm) {
        if (!Validators.isValidSignupForm(signupForm)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "form contains errors, please correct"
            ));
        } else {
            try {
                signupService.saveUser(signupForm);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "error", e.getMessage()
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "redirect", "/signup/confirm.html"
            ));
        }
    }

    @RequestMapping("/confirm.html")
    public String confirmation() {
        return "signup-confirm.html";
    }

    @RequestMapping("/confirm")
    public View confirmCode(String code, Model model) throws Exception {
        try {
            signupService.confirmUser(code);
            var view = new RedirectView("/login");
            view.setExposeModelAttributes(false);
            return view;
        } catch (Exception e) {
            model.addAttribute("error_message", e.getMessage());
            return viewResolver.resolveViewName("error.html", Locale.getDefault());
        }

    }

}
