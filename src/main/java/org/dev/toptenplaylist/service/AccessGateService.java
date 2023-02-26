package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.Role;

import java.util.Set;
import java.util.UUID;

public interface AccessGateService {
    void verifyAccess(Set<Role> allowedRoles, boolean resourceOwnerAllowed, UUID resourceOwnerId);
}
