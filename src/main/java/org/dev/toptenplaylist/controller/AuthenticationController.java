package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.UserCredentials;
import org.dev.toptenplaylist.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public void login(@RequestBody UserCredentials userCredentials) {
        authenticationService.login(userCredentials);
    }

    @DeleteMapping
    public void logout() {
        authenticationService.logout();
    }
}
