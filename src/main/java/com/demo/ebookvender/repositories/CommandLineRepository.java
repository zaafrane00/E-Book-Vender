package com.demo.ebookvender.repositories;


import com.demo.ebookvender.entities.CommandLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommandLineRepository extends JpaRepository<CommandLine, Long> {

    public List<CommandLine> findCommandLinesByCommandId(Long comId);

    public Optional<CommandLine> findCommandLineByCommandIdAndBookId(Long comId, Long bookId);

    public void deleteCommandLinesByCommandId(Long id);
}
