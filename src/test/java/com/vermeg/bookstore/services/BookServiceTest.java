package com.vermeg.bookstore.services;

import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.repositories.BookRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
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

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testing the book service");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the book service");
    }

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
        books.add(new Book(new Long(1),"L'alshimiste","Paulo Coelho",30));
        books.add(new Book(new Long(2),"Remember me","Sophie Kinsella",43));
        books.add(new Book(new Long(5),"Notre dame de Paris","Victor Hugo",28));
        books.add(new Book(new Long(7),"Les misérables","Victor Hugo",30));

        when(this.bookRepository.findAll()).thenReturn(books);
        System.out.println(this.bookService.getAll().size());
        System.out.println(Arrays.toString(this.bookService.getAll().toArray()));
        assertTrue(this.bookService.getAll().size() == books.size(),
                "Test failed: Size of list isn't equal to the size of the present test");
    }

    @Test
    public void getBookByIdTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2008-01-17");
        Book book = new Book(new Long(7),"Madame Bovary","Gustave Flauvert",14, date);
        when(this.bookRepository.findById(book.getId())).thenReturn(java.util.Optional.of(book));
        assertEquals(7, book.getId());
        assertSame(this.bookService.getBookById(book.getId()).getId(),book.getId(),
             "Test failed: Not matching Book ID");
        System.out.println(this.bookService.getBookById(book.getId()).toString());
    }

    @Test
    public void addBookTest() {
        Book book = new Book("book","book's author",4, new Date());

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
        Book book = new Book(new Long(7),"Madame Bovary","Gustave Flauvert",14, date);
        bookService.updateBook(book,new Long(7));
        verify(bookRepository, times(1)).save(book);
    }
}