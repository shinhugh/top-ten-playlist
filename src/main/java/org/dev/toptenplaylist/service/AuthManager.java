package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.UserCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {
    private final AuthenticationManager authenticationManager;

    public AuthManager(AuthenticationManager authenticationManager) {
        this.authenticationManager =  authenticationManager;
    }

    @Override
    public void login(UserCredentials userCredentials) {
        // TODO: Verify provided credentials and grant authentication for current session
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getName(), userCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public void logout() {
        // TODO: Revoke authentication for current session
    }
}
