package com.webapp.controllers.traits;


import com.webapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;


public interface WithStdData {
  @ModelAttribute
  default User addUserDataToModel(Model model, Authentication authentication) {
//    fetch user data
    return User.fromAuthentication(authentication);
  }
}
