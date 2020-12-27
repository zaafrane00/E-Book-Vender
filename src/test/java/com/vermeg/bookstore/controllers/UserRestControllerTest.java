package com.vermeg.bookstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.services.MyUserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    MyUserService userService;

    @InjectMocks
    UserRestController userRestController;

    private String url="http://localhost:8080/";

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testing the user controller");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the user controller");
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
    public void getAllUsersTest() throws Exception {

        List<User> users = new ArrayList<User>();

        when(userService.getAllUsers()).thenReturn(users);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url+"users"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByIdTest() throws Exception {

        User u = new User(new Long(5),"dorrakt", "dorraKt", "ROLE_USER", true);

        when(userService.getUserById(anyLong())).thenReturn(u);

        mockMvc.perform(MockMvcRequestBuilders.get(this.url+"user/5")).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(u.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value(u.getUserName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roles").value(u.getRoles()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(u.isActive()))
                .andExpect(status().isOk());
    }

    @Test
    public void addUserTest() throws Exception {
        User u = new User("dorrakt", "dorraKt", "ROLE_USER", true);

        when(userService.addUser(any(User.class))).thenReturn(u);

        mockMvc.perform(MockMvcRequestBuilders.post(url+"user/add")
                .content(new ObjectMapper().writeValueAsString(u))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void addAdminTest() throws Exception {
        User u = new User("ghaliakt", "ghaliaKt", "ROLE_ADMIN", true);

        when(userService.addAdmin(any(User.class))).thenReturn(u);

        mockMvc.perform(MockMvcRequestBuilders.post(url+"admin/add")
                .content(new ObjectMapper().writeValueAsString(u))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void deleteUserTest() throws Exception {
        User u = new User();

        when(userService.deleteUser(anyLong())).thenReturn(u);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+"user/6/delete")
                .content(new ObjectMapper().writeValueAsString(u))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}