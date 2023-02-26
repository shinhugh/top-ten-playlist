package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.Role;
import org.dev.toptenplaylist.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserDetailsWrapperService implements UserDetailsService {
    private final UserService userService;

    public UserDetailsWrapperService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.readByEmail(username, false);
        }
        catch (NoSuchElementException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return new UserDetailsWrapper(user);
    }

    private record UserDetailsWrapper(User user) implements UserDetails {
        public UserDetailsWrapper(User user) {
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            HashSet<SimpleGrantedAuthority> authorities = new HashSet<>();
            Set<Role> roles = user.getRoles();
            if (roles != null) {
                roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())));
            }
            return authorities;
        }

        @Override
        public String getPassword() {
            return user.getPassword();
        }

        @Override
        public String getUsername() {
            return user.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
