package com.demo.ebookvender.controllers;

import com.demo.ebookvender.entities.CommandLine;
import com.demo.ebookvender.services.CommandLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/command-line/")
public class CommandLineRestController {

    @Autowired
    CommandLineService commandLineService;

    @GetMapping("")
    public ResponseEntity<List<CommandLine>> getAllLines() {
        return new ResponseEntity<>(commandLineService.getAllCommandLines(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CommandLine> getLine(@PathVariable Long id) {
        return new ResponseEntity<>(commandLineService.getCommandLineById(id), HttpStatus.OK);
    }

    @PostMapping("add/user/{userId}/book/{bookId}")
    public ResponseEntity<CommandLine> addLine(@PathVariable Long bookId, @PathVariable Long userId,
                                               @RequestBody @Validated CommandLine c, BindingResult result) throws Exception {
        if (result.hasErrors())
            System.err.println(result.getAllErrors());
        return new ResponseEntity<>(commandLineService.addCommandLine(bookId, c, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommandLine> deleteCommandLine(@PathVariable Long id) throws Exception {
        return new ResponseEntity<CommandLine>(commandLineService.deleteCommandLine(id), HttpStatus.OK);
    }

    @PutMapping("modify/command/{cmdId}/book/{bookId}")
    public CommandLine updateCom(@PathVariable Long cmdId, @PathVariable Long bookId,
                                 @RequestBody @Validated CommandLine c, BindingResult result) throws Exception {
        if (result.hasErrors())
            System.err.println(result.getAllErrors());
        return commandLineService.updateCom(cmdId, bookId, c);
    }
}
