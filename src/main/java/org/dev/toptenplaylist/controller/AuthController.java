package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.SessionCookie;
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
    public void login(@CookieValue(value = "session", required = false) String sessionToken, @RequestBody UserCredentials userCredentials, HttpServletResponse httpResponse) {
        SessionCookie sessionCookie = authenticationService.login(sessionToken, userCredentials);
        addSessionCookie(httpResponse, sessionCookie);
    }

    @DeleteMapping
    public void logout(@CookieValue(value = "session", required = false) String sessionToken, HttpServletResponse httpResponse) {
        authenticationService.logout(sessionToken);
        addSessionCookie(httpResponse, new SessionCookie(null, 0));
    }

    private void addSessionCookie(HttpServletResponse httpResponse, SessionCookie sessionCookie) {
        Cookie cookie = new Cookie("session", sessionCookie.getToken());
        cookie.setPath("/");
        cookie.setMaxAge(sessionCookie.getMaxAge());
        httpResponse.addCookie(cookie);
    }
}
