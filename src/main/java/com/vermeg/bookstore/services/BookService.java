package com.vermeg.bookstore.services;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) throws ResourceNotFoundException {
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The book " +
                "with the ID "+id+" does not exist"));
    }

    public Book addBook(Book b) {
        return bookRepository.save(b);
    }

    public Book deleteBook(Long id) throws ResourceNotFoundException {
        Book b = bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("The " +
                "book with the ID "+id+" does not exist"));
        bookRepository.deleteById(id);
        return b;
    }

    public Book updateBook(Book b,  Long id) throws ResourceNotFoundException {
        bookRepository.findById(id).map(book -> {
            System.out.println(book.toString());
            book.setAuthor(b.getAuthor());
            book.setPrice(b.getPrice());
            book.setReleaseDate(b.getReleaseDate());
            book.setTitle(b.getTitle());
            return bookRepository.save(book);
        }).orElseThrow(()-> new ResourceNotFoundException("The book " +
                        "with the ID "+id+" does not exist"));
        return null;
    }

}
