package com.vermeg.bookstore.repositories;

import com.vermeg.bookstore.entities.Command;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CommandRepository extends JpaRepository<Command, Long> {

    public List<Command> getCommandsByCreationDate(Date cmdDate);

    public Optional<Command> findCommandByUserIdAndAndWindedUp(Long userId, boolean windedUp);

    public List<Command> getCommandsByUserId(Long userId);
}
