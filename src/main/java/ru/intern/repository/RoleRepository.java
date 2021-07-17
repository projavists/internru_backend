package ru.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.intern.entity.RoleEntity;

/**
 * @author Kir
 * Created on 17.07.2021
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findRoleByName(String name);
}
