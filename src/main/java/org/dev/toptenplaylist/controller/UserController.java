package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.service.SessionTokenService;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final SessionTokenService sessionTokenService;
    private final UserService userService;

    public UserController(SessionTokenService sessionTokenService, UserService userService) {
        this.sessionTokenService = sessionTokenService;
        this.userService = userService;
    }

    @PostMapping
    public void create(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) User user) {
        sessionTokenService.handleSessionToken(response, sessionToken);
        userService.create(user);
    }

    @GetMapping("/id/{id}")
    public User readById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return userService.readById(activeUserAccountId, id);
    }

    @GetMapping("/session")
    public User readBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return userService.readByActiveUserAccountId(activeUserAccountId);
    }

    @GetMapping("/public-name/{publicName}")
    public User readByPublicName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String publicName) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return userService.readByPublicName(activeUserAccountId, publicName);
    }

    @PutMapping("/id/{id}")
    public void updateById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id, @RequestBody(required = false) User user) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.updateById(activeUserAccountId, id, user);
    }

    @PutMapping("/session")
    public void updateBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @RequestBody(required = false) User user) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.updateByActiveUserAccountId(activeUserAccountId, user);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.deleteById(activeUserAccountId, id);
    }

    @DeleteMapping("/session")
    public void deleteBySession(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken) {
        String activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.deleteByActiveUserAccountId(activeUserAccountId);
    }
}
