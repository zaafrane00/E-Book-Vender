package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.Book;
import com.demo.ebookvender.entities.Command;
import com.demo.ebookvender.entities.CommandLine;
import com.demo.ebookvender.repositories.CommandLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandLineService {

    @Autowired
    BookService bookService;

    @Autowired
    CommandLineRepository commandLineRepository;

    @Autowired
    MyUserService userService;

    @Autowired
    CommandService commandService;

    public List<CommandLine> getAllCommandLines() {
        return commandLineRepository.findAll();
    }

    public CommandLine getCommandLineById(Long id) {
        return commandLineRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "No command line with this id"));
    }


    public CommandLine addCommandLine(Long bookId, CommandLine commandLine,
                                      Long userId) throws Exception {
        Book b = bookService.getBookById(bookId);
        Optional<Command> c = commandService.getUserActiveCommand(userId);
        // if the user still has a command which is not winded up
        if (c.isPresent()) {
            commandLine.setCommand(c.get());
            System.err.println(true);
            //test if the command already has a command line containing the book id
            if (commandLineRepository.findCommandLineByCommandIdAndBookId(c.get().getId(), bookId).isPresent())
                throw new Exception("You have already commanded this book");
        } else {
            System.err.println(false);
            // else create a new command
            Command cc = new Command();
            cc.setUser(userService.getUserById(userId));
            commandLine.setCommand(commandService.createCommand(cc));
        }
        commandLine.setBook(b);
        return commandLineRepository.save(commandLine);
    }

    public CommandLine deleteCommandLine(Long clId) throws Exception {
        CommandLine c = getCommandLineById(clId);
        if (commandService.getCmdById(c.getCommand().getId()).isWindedUp()) {
            throw new Exception("This command is " +
                    "winded you can't modify its lines");
        }
        if (commandLineRepository.findCommandLinesByCommandId(c.getCommand().getId()).size() == 1)
            commandService.deleteCommand(c.getCommand().getId());
        else
            commandLineRepository.deleteById(clId);
        return c;
    }

    public CommandLine updateCom(Long coId, Long bId, CommandLine c) throws Exception {
        System.out.println(coId);
        if (commandService.getCmdById(coId).isWindedUp()) {
            throw new Exception("This command is " +
                    "winded you can't modify its lines");
        }
        CommandLine cl = commandLineRepository.findCommandLineByCommandIdAndBookId(coId, bId).get();
        cl.setQuantity(c.getQuantity());
        return commandLineRepository.save(cl);
    }

}
