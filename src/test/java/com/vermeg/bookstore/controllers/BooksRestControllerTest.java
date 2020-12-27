package com.vermeg.bookstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.services.BookService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

//https://spring.io/guides/gs/testing-web/
 /*The @SpringBootTest annotation tells Spring Boot to look for a main configuration class (one
with @SpringBootApplication, for instance) and use that to start a Spring application  context. */

/* @AutoConfigureMockMvc annotation is used to ask spring to inject MockMvc: The code will
be called in exactly the same way as if it were processing a real HTTP request but without the cost
 of starting the server. */
public class BooksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    /*Les doublures d'objets ou les objets de type mock permettent de simuler le comportement
    d'autres objets. Ils peuvent trouver de nombreuses utilités notamment dans les tests
    unitaires où ils permettent de tester le code en maitrisant le comportement des dépendances.*/
    private BookService bookService;

    @InjectMocks
    private BooksRestController bookController;

    private String url = "http://localhost:8080/bookstore/";


    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testing the book controller");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the book controller");
    }


    @BeforeEach
    public void setUp() {
        System.out.println("The test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("The test ended");
    }

    @Test
    public void getAllBooksTest() throws Exception {
        List<Book> books = new ArrayList<Book>();
/*
        books.add(new Book(new Long(1),"Les misérables","Victor Hugo",25,null));
        books.add(new Book(new Long(2),"Notre dame de Paris","Victor Hugo",19.8,null));
        books.add(new Book(new Long(3),"L'alshimiste","Paulo Coelho",30.65,null));
*/

        when(bookService.getAll()).thenReturn(books);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(4)))
                .andExpect(status().isOk());
    }

    @Test
    public void getBookByIdTest() throws Exception {
        /* Steps:
            1- mock the data returned by the book service class
            2- create a mock http request to verify the expected result
        * */

        //mock the data returned by the book service class
        Book book =new Book();
        when(bookService.getBookById(anyLong())).thenReturn(book);

        // create a mock http request to verify the expected result
        mockMvc.perform(MockMvcRequestBuilders.get(this.url+"book/1")).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("L'alshimiste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Paulo Coelho"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(30))
                .andExpect(status().isOk());
    }

    @Test
    public void addBookTest() throws Exception {
        Book book = new Book("Les misérables", "Victor Hugo", 30, new Date());

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.post(url+"book/add")
                .content(new ObjectMapper().writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Remember me"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Sophie Kinsella"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(43));*/
                .andDo(print());
    }

    @Test
    public void deleteBookTest() throws Exception {
        Book book = new Book();

        when(bookService.deleteBook(anyLong())).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+"book/4/delete")
                .content(new ObjectMapper().writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateBookTest() throws Exception {
        Book book = new Book(new Long(2),"Remember me", "Sophie Kinsella", 43, new Date());

        when(bookService.updateBook(any(Book.class), anyLong())).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.put(url+"book/2/modify")
                .content(new ObjectMapper().writeValueAsString(book))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}