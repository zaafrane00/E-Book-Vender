package com.vermeg.bookstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vermeg.bookstore.entities.Book;
import com.vermeg.bookstore.entities.CommandLine;
import com.vermeg.bookstore.services.CommandLineService;
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

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CommandLineRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    CommandLineService commandLineService;

    @InjectMocks
    CommandLineRestController lineRestController;
    private String url= "http://localhost:8080/command-line/";

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testiong the Command line controller");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the command line controller");
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
    public void getAllLinesTest() throws Exception {
        List<CommandLine> lines = new ArrayList<>();

        when(commandLineService.getAllCommandLines()).thenReturn(lines);

        this.mockMvc.perform(MockMvcRequestBuilders.get(this.url))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(5)))
                .andExpect(status().isOk());
    }

    @Test
    public void getLineTest() throws Exception {
        CommandLine  line = new CommandLine();

        when(commandLineService.getCommandLineById(new Long(8))).thenReturn(line);

        mockMvc.perform(MockMvcRequestBuilders.get(this.url+"8")).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.book.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.command.id").value(10))
                .andExpect(status().isOk());
    }

    @Test
    public void addLineTest() throws Exception {
        CommandLine c = new CommandLine(2);

        when(commandLineService.addCommandLine(anyLong(), any(CommandLine.class),
                anyLong())).thenReturn(c);

        mockMvc.perform(MockMvcRequestBuilders.post(url+"add/user/4/book/7")
                .content(new ObjectMapper().writeValueAsString(c))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void deleteCommandLineTest() throws Exception {
        CommandLine c = new CommandLine();

        when(commandLineService.deleteCommandLine(anyLong())).thenReturn(c);

        mockMvc.perform(MockMvcRequestBuilders.delete(url+"13")
                .content(new ObjectMapper().writeValueAsString(c))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateComTest() throws Exception {
        CommandLine c = new CommandLine(4);

        when(commandLineService.updateCom(anyLong(),anyLong(), any(CommandLine.class))).thenReturn(c);

        mockMvc.perform(MockMvcRequestBuilders.put(url+"modify/command/13/book/1")
                .content(new ObjectMapper().writeValueAsString(c))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}