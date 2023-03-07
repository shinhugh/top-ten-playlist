package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.AuthenticationResult;
import org.dev.toptenplaylist.model.LoginCredentials;
import org.dev.toptenplaylist.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public void login(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) LoginCredentials loginCredentials) {
        AuthenticationResult result = authenticationService.login(sessionToken, loginCredentials);
        if (result.isShouldSetCookie()) {
            setSessionCookie(response, result.getSessionToken(), result.getSessionCookieMaxAge());
        }
        if (result.getException() != null) {
            throw result.getException();
        }
    }

    @DeleteMapping
    public void logout(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken) {
        AuthenticationResult result = authenticationService.logout(sessionToken);
        if (result.isShouldSetCookie()) {
            setSessionCookie(response, result.getSessionToken(), result.getSessionCookieMaxAge());
        }
        if (result.getException() != null) {
            throw result.getException();
        }
    }

    private void setSessionCookie(HttpServletResponse response, String token, int maxAge) {
        response.addHeader("Set-Cookie", "session=" + token + "; Path=/; Max-Age=" + maxAge + "; SameSite=strict");
    }
}
