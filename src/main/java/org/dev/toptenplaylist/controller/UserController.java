package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.Role;
import org.dev.toptenplaylist.model.User;
import org.dev.toptenplaylist.service.AccessGateService;
import org.dev.toptenplaylist.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AccessGateService accessGateService;

    public UserController(UserService userService, AccessGateService accessGateService) {
        this.userService = userService;
        this.accessGateService = accessGateService;
    }

    @GetMapping
    public Set<User> read() {
        HashSet<Role> allowedRoles = new HashSet<>(List.of(Role.ADMIN));
        accessGateService.verifyAccess(allowedRoles, false, null);
        return userService.readAll(true);
    }

    @PostMapping
    public UUID create(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public User read(@PathVariable UUID id) {
        HashSet<Role> allowedRoles = new HashSet<>(List.of(Role.ADMIN));
        accessGateService.verifyAccess(allowedRoles, true, id);
        return userService.readById(id, true);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable UUID id, @RequestBody User user) {
        HashSet<Role> allowedRoles = new HashSet<>(List.of(Role.ADMIN));
        accessGateService.verifyAccess(allowedRoles, true, id);
        userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        HashSet<Role> allowedRoles = new HashSet<>(List.of(Role.ADMIN));
        accessGateService.verifyAccess(allowedRoles, true, id);
        userService.delete(id);
    }
}
