package com.example.subwhere.controller;

import com.example.subwhere.config.auth.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model, @SessionAttribute(name = "user", required = false) SessionUser user) {

        if (user != null) {
            model.addAttribute("name", user.getName());
        }

        return "index";
    }
}
