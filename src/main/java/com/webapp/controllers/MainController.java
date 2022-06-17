package com.webapp.controllers;

import com.webapp.controllers.traits.WithStdData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController implements WithStdData {



    @RequestMapping
    public String main(Model model) {
        return "main.html";
    }


}
