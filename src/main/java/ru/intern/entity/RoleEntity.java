package ru.intern.entity;

import javax.persistence.*;

/**
 * @author Kir
 * Created on 17.07.2021
 */
@Entity
@Table(name = "role", schema = "public")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
