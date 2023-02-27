package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.UserCredentials;
import org.dev.toptenplaylist.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public void login(@RequestBody UserCredentials userCredentials) {
        authService.login(userCredentials);
    }

    @DeleteMapping
    public void logout() {
        authService.logout();
    }
}
