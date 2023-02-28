package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.UserCredentials;
import org.dev.toptenplaylist.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public void login(@CookieValue("session") String sessionToken, @RequestBody UserCredentials userCredentials) {
        // TODO: Is sessionToken null if cookie doesn't exist?
        System.out.println("@@ DEBUG: sessionToken: " + (sessionToken == null ? "null" : sessionToken)); // DEBUG
        String token = authenticationService.login(sessionToken, userCredentials);
        // TODO: Set client cookie
    }

    @DeleteMapping
    public void logout(@CookieValue("session") String sessionToken) {
        authenticationService.logout(sessionToken);
        // TODO: Unset client cookie
    }
}
