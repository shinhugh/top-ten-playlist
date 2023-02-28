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
    public void login(@RequestBody UserCredentials userCredentials) {
        String token = authenticationService.login(userCredentials);
        // TODO: Set client cookie
    }

    @DeleteMapping
    public void logout(@CookieValue("session") String sessionToken) {
        authenticationService.logout(sessionToken);
        // TODO: Unset client cookie
    }
}
