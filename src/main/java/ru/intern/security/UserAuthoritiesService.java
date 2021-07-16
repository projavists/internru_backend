package ru.intern.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import ru.intern.bean.UserBean;
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
        UserBean ub = userRepository.findUserByLogin(login);
        if (login != null) {
            UserAuthorities user = new UserAuthorities();
            user.setId(ub.getId());
            user.setLogin(ub.getLogin());
            user.setPassword(ub.getPassword());
            return user;
        }
        throw new UsernameNotFoundException(null);
    }
}
