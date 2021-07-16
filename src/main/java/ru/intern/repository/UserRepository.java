package ru.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intern.bean.UserBean;

/**
 * @author Kir
 * Created on 16.07.2021
 */
@Repository
public interface UserRepository extends JpaRepository<UserBean, Long> {
    UserBean findUserByLogin(String login);
}
