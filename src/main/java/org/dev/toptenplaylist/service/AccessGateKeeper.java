package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.Role;
import org.dev.toptenplaylist.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

@Service
public class AccessGateKeeper implements AccessGateService {
    private final UserService userService;

    public AccessGateKeeper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void verifyAccess(Set<Role> allowedRoles, boolean resourceOwnerAllowed, UUID resourceOwnerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Access denied");
        }

        User user;
        try {
            user = userService.readByEmail(authentication.getName(), true);
        }
        catch (NoSuchElementException ex) {
            throw new AccessDeniedException("Access denied");
        }

        if (allowedRoles != null && user.getRoles().stream().anyMatch(allowedRoles::contains)) {
            return;
        }

        if (resourceOwnerAllowed && user.getId().equals(resourceOwnerId)) {
            return;
        }

        throw new AccessDeniedException("Access denied");
    }
}
