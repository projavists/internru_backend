package ru.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intern.entity.UserEntity;

/**
 * @author Kir
 * Created on 16.07.2021
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByLogin(String login);
}
