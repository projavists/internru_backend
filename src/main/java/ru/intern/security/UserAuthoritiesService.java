package ru.intern.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.intern.entity.UserEntity;
import ru.intern.repository.UserRepository;

/**
 * @author Kir
 * Created on 16.07.2021
 */

public class UserAuthoritiesService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthoritiesService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserAuthorities loadUserByUsername(String login) {
        UserEntity ub = userRepository.findUserByLogin(login);
        if (ub != null)
            return UserAuthorities.fromUserEntityToUserAuthorities(ub);
        throw new UsernameNotFoundException("username not found");
    }
}
