package org.dev.toptenplaylist.service;

import org.dev.toptenplaylist.model.UserAccount;
import org.dev.toptenplaylist.repository.UserAccountRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;

@Service
public class UserDetailsWrapperService implements UserDetailsService {
    private final UserAccountRepository userAccountRepository;

    public UserDetailsWrapperService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount;
        try {
            userAccount = userAccountRepository.readByName(username);
        }
        catch (NoSuchElementException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return new UserDetailsWrapper(userAccount);
    }

    private record UserDetailsWrapper(UserAccount userAccount) implements UserDetails {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new HashSet<>();
        }

        @Override
        public String getPassword() {
            return userAccount.getPasswordHash();
        }

        @Override
        public String getUsername() {
            return userAccount.getName();
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
