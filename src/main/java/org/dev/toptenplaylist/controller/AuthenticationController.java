package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.UserCredentials;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @PostMapping
    public void login(@RequestBody UserCredentials userCredentials) {
        // TODO: Verify provided credentials and authenticate current session
    }

    @DeleteMapping
    public void logout() {
        // TODO: Revoke authentication for current session
    }
}
