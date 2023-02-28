package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.UserCredentials;
import org.dev.toptenplaylist.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public void login(@RequestBody UserCredentials userCredentials, HttpServletResponse httpResponse) {
        // TODO: Trying to login on valid session should fail and maintain existing session
        String token = authenticationService.login(userCredentials);
        Cookie sessionCookie = new Cookie("session", token);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(60);
        httpResponse.addCookie(sessionCookie);
    }

    @DeleteMapping
    public void logout(@CookieValue("session") String sessionToken, HttpServletResponse httpResponse) {
        // TODO: Handle requests without session cookie as well (by responding with 200 OK)
        authenticationService.logout(sessionToken);
        Cookie sessionCookie = new Cookie("session", null);
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(0);
        httpResponse.addCookie(sessionCookie);
    }
}
