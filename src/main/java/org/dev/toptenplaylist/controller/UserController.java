package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.service.SessionTokenService;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        UUID convertedId = null;
        try {
            convertedId = UUID.fromString(id);
        }
        catch (IllegalArgumentException ignored) { }
        return userService.readById(activeUserAccountId, convertedId);
    }

    @GetMapping("/login-name/{loginName}")
    public User readByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return userService.readByLoginName(activeUserAccountId, loginName);
    }

    @GetMapping("/public-name/{publicName}")
    public User readByPublicName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String publicName) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        return userService.readByPublicName(activeUserAccountId, publicName);
    }

    @PutMapping("/id/{id}")
    public void updateById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id, @RequestBody(required = false) User user) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        UUID convertedId = null;
        try {
            convertedId = UUID.fromString(id);
        }
        catch (IllegalArgumentException ignored) { }
        userService.updateById(activeUserAccountId, convertedId, user);
    }

    @PutMapping("/login-name/{loginName}")
    public void updateByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName, @RequestBody(required = false) User user) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.updateByLoginName(activeUserAccountId, loginName, user);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String id) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        UUID convertedId = null;
        try {
            convertedId = UUID.fromString(id);
        }
        catch (IllegalArgumentException ignored) { }
        userService.deleteById(activeUserAccountId, convertedId);
    }

    @DeleteMapping("/login-name/{loginName}")
    public void deleteByLoginName(HttpServletResponse response, @CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        UUID activeUserAccountId = sessionTokenService.handleSessionToken(response, sessionToken);
        userService.deleteByLoginName(activeUserAccountId, loginName);
    }
}
