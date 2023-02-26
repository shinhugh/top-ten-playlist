package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Set<User> read() {
        // TODO
        return null;
    }

    @PostMapping
    public UUID create(@RequestBody User user) {
        // TODO
        return null;
    }

    @GetMapping("/{id}")
    public User read(@PathVariable UUID id) {
        // TODO
        return null;
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody User user) {
        // TODO
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        // TODO
    }
}
