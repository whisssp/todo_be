package com.todo_quqo.repository;

import com.todo_quqo.entity.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(@Param("email") String email);

}