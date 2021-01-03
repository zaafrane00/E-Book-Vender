package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.Book;
import com.demo.ebookvender.repositories.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @BeforeEach
    public void setUp() {
        System.out.println("The test started");
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("The test ended");
    }

    @Test
    public void getAllTest() {
        List<Book> books = new ArrayList<Book>();
        books.add(new Book(new Long(1), "Title1", "auther1", 10));
        books.add(new Book(new Long(2), "Title2", "auther2", 11));
        books.add(new Book(new Long(3), "Title3", "auther3", 12));
        books.add(new Book(new Long(4), "Title4", "auther4", 50));
        when(this.bookRepository.findAll()).thenReturn(books);
        System.out.println(this.bookService.getAll().size());
        System.out.println(Arrays.toString(this.bookService.getAll().toArray()));
        assertTrue(this.bookService.getAll().size() == books.size(),
                "Error");
    }

    @Test
    public void getBookByIdTest() throws ParseException {
       /* DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2008-01-17");*/
        Book book = new Book(new Long(7), "Title", "auther", 50, "2008-01-17");
        when(this.bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
        assertEquals(7, book.getId());
        assertSame(this.bookService.getBookById(book.getId()).getId(), book.getId(),
                "Error");
        System.out.println(this.bookService.getBookById(book.getId()).toString());
    }

    @Test
    public void addBookTest() {
        Book book = new Book("title", "auther", 4, "2008-01-17");
        bookService.addBook(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void deleteBookTest() throws ParseException {
        bookService.deleteBook(new Long(2));
        verify(bookRepository, times(2)).deleteById(new Long(2));
    }

    @Test
    public void updateBookTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2008-01-17");
        Book book = new Book(new Long(7), "title", "auther", 50, "2008-01-17");
        bookService.updateBook(book, new Long(7));
        verify(bookRepository, times(1)).save(book);
    }
}