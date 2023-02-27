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
    public User read(@PathVariable String loginName) {
        return userService.readByLoginName(loginName);
    }

    @PutMapping("/{loginName}")
    public void update(@PathVariable String loginName, @RequestBody User user) {
        userService.updateByLoginName(loginName, user);
    }

    @DeleteMapping("/{loginName}")
    public void delete(@PathVariable String loginName) {
        userService.deleteByLoginName(loginName);
    }
}
