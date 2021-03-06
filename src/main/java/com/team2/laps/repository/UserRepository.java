package com.team2.laps.repository;

import java.util.List;
import java.util.Optional;

import com.team2.laps.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNameOrEmail(String username, String email);

    List<User> findByIdIn(List<String> userIds);

    Optional<User> findByName(String name);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

    List<User> findAll();
}
