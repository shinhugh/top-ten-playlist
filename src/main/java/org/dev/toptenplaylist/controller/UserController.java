package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void create(@RequestBody User user) {
        userService.create(user);
    }

    @GetMapping("/{loginName}")
    public User read(@CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        return userService.readByLoginName(sessionToken, loginName);
    }

    @PutMapping("/{loginName}")
    public void update(@CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName, @RequestBody User user) {
        userService.updateByLoginName(sessionToken, loginName, user);
    }

    @DeleteMapping("/{loginName}")
    public void delete(@CookieValue(value = "session", required = false) String sessionToken, @PathVariable String loginName) {
        userService.deleteByLoginName(sessionToken, loginName);
    }
}
