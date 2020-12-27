package com.demo.ebookvender.repositories;

import com.demo.ebookvender.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> getUserByEmail(String email);

    public List<User> getUsersByRoles(String role);
}
