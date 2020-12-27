package com.vermeg.bookstore.controllers;

import com.vermeg.bookstore.entities.Command;
import com.vermeg.bookstore.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/command/")
public class CommandRestController {

    @Autowired
    CommandService commandService;

    @GetMapping("")
    public ResponseEntity<List<Command>> getAllCommand(){
        return new ResponseEntity<List<Command>>(commandService.getAllCommand(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable Long id) {
        return new ResponseEntity<>(commandService.getCmdById(id), HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Command>> getCommandByUser(@PathVariable Long id) {
        return new ResponseEntity<>(commandService.getUserCommands(id), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<Command> createCommand(@RequestBody @Validated Command c) {
        return new ResponseEntity<>(commandService.createCommand(c), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Command> deleteCommand(@PathVariable Long id) {
        return new ResponseEntity<>(commandService.deleteCommand(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Double> windUpCommand(@PathVariable Long id){
        return new ResponseEntity<>(commandService.windUpCommand(id), HttpStatus.OK);
    }

    @GetMapping("{id}/total")
    public ResponseEntity<Double> getTotal(@PathVariable Long id){
        return new ResponseEntity<>(commandService.getTotalPrice(id), HttpStatus.OK);
    }
}
