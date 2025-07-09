package com.codegym.bookstore_management.config.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.codegym.bookstore_management.service.UserService;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.codegym.bookstore_management.model.User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String email = user.getEmail();
        String password = user.getPassword();
        String roleName = user.getRole() != null ? user.getRole().getName() : "USER";
        boolean accountNonLocked = true;
        boolean enabled = true;

        UserDetails sUser = User.builder()
                .username(email)
                .password(password)
                .roles(roleName)
                .accountLocked(!accountNonLocked)
                .disabled(!enabled)
                .build();
        return sUser;
    }

}
