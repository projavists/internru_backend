package ru.intern.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.intern.entity.RoleEntity;
import ru.intern.entity.UserEntity;
import ru.intern.repository.RoleRepository;
import ru.intern.repository.UserRepository;

/**
 * @author Kir
 * Created on 17.07.2021
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userEntityRepository;
    @Autowired
    private RoleRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity save(UserEntity user) {
        RoleEntity userRole = roleEntityRepository.findRoleByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userEntityRepository.save(user);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findUserByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String username, String password) {
        UserEntity userEntity = userEntityRepository.findUserByLogin(username);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword()))
                return userEntity;
        }
        throw new BadCredentialsException("wrong login or password");
    }
}
