package com.demo.ebookvender.controllers;

import com.demo.ebookvender.entities.Book;
import com.demo.ebookvender.repositories.BookRepository;
import com.demo.ebookvender.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4002/")
@RestController
@RequestMapping("/bookstore/")
public class BooksRestController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> getAll() {
        return new ResponseEntity<List<Book>>(bookService.getAll(), HttpStatus.OK);
    }

    @GetMapping("book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return new ResponseEntity<Book>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("book/add")
    public ResponseEntity<Book> addBook(@RequestBody @Validated Book b, BindingResult result) {
        if (result.hasErrors())
            System.err.println(result.getAllErrors());
        return new ResponseEntity<Book>(bookService.addBook(b), HttpStatus.CREATED);
    }

    @DeleteMapping("book/{id}/delete")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        return new ResponseEntity<Book>(bookService.deleteBook(id), HttpStatus.OK);
    }

    @PutMapping("book/{id}/modify")
    public ResponseEntity<Book> updateBook(@RequestBody @Validated Book b, @PathVariable Long id,
                                           BindingResult result) {
        if (result.hasErrors())
            System.err.println(result.getAllErrors());
        return new ResponseEntity<Book>(bookService.updateBook(b, id), HttpStatus.OK);
    }
}
