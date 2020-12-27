package com.vermeg.bookstore.services;

import com.vermeg.bookstore.entities.Command;
import com.vermeg.bookstore.entities.CommandLine;
import com.vermeg.bookstore.repositories.CommandLineRepository;
import com.vermeg.bookstore.repositories.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CommandService {

    @Autowired
    CommandLineRepository commandLineRepository;

    @Autowired
    CommandRepository commandRepository;

    public List<Command> getAllCommand() {
        return commandRepository.findAll();
    }

    public Command getCmdById(Long id) {
        return commandRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("No command found with the ID " + id)
        );
    }

    public List<Command> getCmdByCreationDate(Date creation) {
        return commandRepository.getCommandsByCreationDate(creation);
    }

    public Command createCommand(Command c) {
        return commandRepository.save(c);
    }

    @Transactional
    public Command deleteCommand(Long comId) {
        Command c =
                commandRepository.findById(comId).orElseThrow(()-> new ResourceNotFoundException(
                        "No command found with the ID " + comId));
        commandLineRepository.deleteCommandLinesByCommandId(comId);
        commandRepository.deleteById(comId);
        return c;
    }

    public Optional<Command> getUserActiveCommand(Long userId) {
        return commandRepository.findCommandByUserIdAndAndWindedUp(userId, false);
    }

    public List<Command> getUserCommands(Long userId) {
        return commandRepository.getCommandsByUserId(userId);
    }

    public double windUpCommand(Long comId) {
        Command c =
                commandRepository.findById(comId).orElseThrow(()-> new ResourceNotFoundException(
                        "No command found with the ID " + comId));
        if (!c.isWindedUp()) {
            c.setWindedUp(true);
            commandRepository.save(c);
        }
        return getTotalPrice(comId);
    }

    public double getTotalPrice(Long comId) {
        Command c =
                commandRepository.findById(comId).orElseThrow(()-> new ResourceNotFoundException(
                        "No command found with the ID " + comId));
        double total = 0;
        List<CommandLine> commandLines =  commandLineRepository.findCommandLinesByCommandId(comId);
        for (CommandLine commandLine: commandLines){
            total +=commandLine.getQuantity()*commandLine.getBook().getPrice();
        }
        return total;
    }

}
