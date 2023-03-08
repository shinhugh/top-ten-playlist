package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.service.SessionTokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@Controller
public class PageController {
    private final SessionTokenService sessionTokenService;

    public PageController(SessionTokenService sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @GetMapping("/")
    public String homePage(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, Model model) {
        sessionTokenService.handleSessionToken(response, sessionToken);
        model.addAttribute("initialPath", "/");
        return "app";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, Model model) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        if (activeUserAccountId != null) {
            return "redirect:/";
        }
        model.addAttribute("initialPath", "/login");
        return "app";
    }

    @GetMapping("/signup")
    public String signUpPage(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, Model model) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        if (activeUserAccountId != null) {
            return "redirect:/";
        }
        model.addAttribute("initialPath", "/signup");
        return "app";
    }

    @GetMapping("/account")
    public String accountPage(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, Model model) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        if (activeUserAccountId == null) {
            return "redirect:/login";
        }
        model.addAttribute("initialPath", "/account");
        return "app";
    }

    @GetMapping("/playlist/{userPublicName}")
    public String playlistPage(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String userPublicName, Model model) {
        sessionTokenService.handleSessionToken(response, sessionToken);
        model.addAttribute("initialPath", "/playlist/" + userPublicName);
        return "app";
    }
}
