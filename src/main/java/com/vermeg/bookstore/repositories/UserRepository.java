package com.vermeg.bookstore.repositories;

import com.vermeg.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> getUserByUserName(String userName);

    public List<User> getUsersByRoles(String role);
}
