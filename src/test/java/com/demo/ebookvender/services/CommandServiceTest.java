package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.Command;
import com.demo.ebookvender.entities.User;
import com.demo.ebookvender.repositories.CommandRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CommandServiceTest {

    @Mock
    CommandRepository commandRepository;

    @InjectMocks
    CommandService commandService;

    @BeforeEach
    public void setUp() {
        System.out.println("The test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("The test ended");
    }

    @Test
    public void getAllCommandTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Command> commands = new ArrayList<>();
        commands.add(new Command(new Long(10), formatter.parse("2008-01-17 19:30:59"), false,
                new User(new Long(1))));
        when(this.commandRepository.findAll()).thenReturn(commands);
        assertTrue(this.commandService.getAllCommand().size() == commands.size(),
                "Error");
    }

    @Test
    public void getCmdByIdTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10), formatter.parse("2008-01-17 19:30:59"), false,
                new User(new Long(1)));
        when(this.commandRepository.findById(anyLong())).thenReturn(java.util.Optional.of(c));
        assertEquals(10, c.getId());
        assertSame(this.commandService.getCmdById(c.getId()).getId(), c.getId(),
                "Error");
    }

    @Test
    public void deleteCommandTest() {
        commandService.deleteCommand(new Long(10));
        verify(commandRepository, times(1)).deleteById(new Long(10));
    }

    @Test
    public void getUserCommandsTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Command> commands = new ArrayList<>();
        commands.add(new Command(new Long(10), formatter.parse("2008-01-17 19:30:59"), false,
                new User(new Long(1))));
        when(this.commandRepository.getCommandsByUserId(anyLong())).thenReturn(commands);
        assertTrue(this.commandService.getUserCommands(commands.get(0).getUser().getId()).size() == commands.size(),
                "Error");
    }

    @Test
    public void windUpCommandTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10), formatter.parse("2020-01-17 05:30:59"), true,
                new User(new Long(1)));
        commandService.windUpCommand(new Long(10));
        verify(commandRepository, times(1)).save(c);
    }

    @Test
    public void getTotalPriceTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10), formatter.parse("2008-01-17 19:30:59"), true,
                new User(new Long(1)));
        commandService.getTotalPrice(new Long(c.getId()));
        verify(commandRepository, times(1)).findById(c.getId());
    }
}