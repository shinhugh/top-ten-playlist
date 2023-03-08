package org.dev.toptenplaylist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("initialPath", "/");
        return "app";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("initialPath", "/login");
        return "app";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("initialPath", "/signup");
        return "app";
    }

    @GetMapping("/account")
    public String accountPage(Model model) {
        model.addAttribute("initialPath", "/account");
        return "app";
    }

    @GetMapping("/playlist/{userPublicName}")
    public String playlistPage(@PathVariable String userPublicName, Model model) {
        model.addAttribute("initialPath", "/playlist/" + userPublicName);
        return "app";
    }
}
