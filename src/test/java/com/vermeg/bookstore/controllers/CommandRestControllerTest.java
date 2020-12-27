package com.vermeg.bookstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Command;
import com.vermeg.bookstore.services.CommandService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CommandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CommandService commandService;

    @InjectMocks
    CommandRestController commandController;
    private String url= "http://localhost:8080/command/";

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testiong the Command controller");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the command controller");
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
    void getAllCommandTest() throws Exception {
        List<Command> commands = new ArrayList<>();
        when(commandService.getAllCommand()).thenReturn(commands);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(status().isOk());
    }

    @Test
    void getCommandByIdTest() throws Exception {
        Command command= new Command();

        when(commandService.getCmdById(anyLong())).thenReturn(command);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url+10))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windedUp").value(true))
                .andExpect(status().isOk());
    }

    @Test
    void getCommandByUserTest() throws Exception {
        List<Command> commands = new ArrayList<>();

        when(commandService.getUserCommands(anyLong())).thenReturn(commands);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url+"user/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCommandTest() throws Exception {
        Command command = new Command();

        when(commandService.deleteCommand(anyLong())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+13)
                .content(new ObjectMapper().writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void windUpCommandTest() throws Exception {
        double total = 0;
        when(commandService.windUpCommand(anyLong())).thenReturn(total);

        mockMvc.perform(MockMvcRequestBuilders.put(url+10)
                .content(new ObjectMapper().writeValueAsString(total))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getTotalTest() throws Exception {
        double total = 0;
        when(commandService.windUpCommand(anyLong())).thenReturn(total);

        mockMvc.perform(MockMvcRequestBuilders.put(url+10)
                .content(new ObjectMapper().writeValueAsString(total))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}