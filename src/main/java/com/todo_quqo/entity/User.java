package com.todo_quqo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "password", length = 50)
    private String password;

}