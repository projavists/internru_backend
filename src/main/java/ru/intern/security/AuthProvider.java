package ru.intern.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Kir
 * Created on 16.07.2021
 */

public class AuthProvider implements AuthenticationProvider {

    private final UserAuthoritiesService userAuthoritiesService;
    private final PasswordEncoder passwordEncoder;

    public AuthProvider(UserAuthoritiesService userWithAuthoritiesService, PasswordEncoder passwordEncoder) {
        this.userAuthoritiesService = userWithAuthoritiesService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (password == null) {
            throw new BadCredentialsException("Password is empty.");
        }

        UserAuthorities user = userAuthoritiesService.loadUserByUsername(username);

        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Username not found.");
        }

        if (!passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
