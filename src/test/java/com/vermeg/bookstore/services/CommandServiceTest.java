package com.vermeg.bookstore.services;

import com.vermeg.bookstore.entities.Command;
import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.repositories.CommandRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Start testiong the Command service");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("End testing the command service");
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
    public void getAllCommandTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Command> commands= new ArrayList<>();
        commands.add(new Command(new Long(10),formatter.parse("2008-01-17 19:30:59" ), false,
                new User(new Long(1))));

        when(this.commandRepository.findAll()).thenReturn(commands);
        assertTrue(this.commandService.getAllCommand().size() == commands.size(),
                "Test failed: Size of list isn't equal to the size of the present test");
    }

    @Test
    public void getCmdByIdTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10),formatter.parse("2008-01-17 19:30:59" ), false,
                new User(new Long(1)));

        when(this.commandRepository.findById(anyLong())).thenReturn(java.util.Optional.of(c));
        assertEquals(10, c.getId());
        assertSame(this.commandService.getCmdById(c.getId()).getId(),c.getId(),
                "Test failed: Not matching command ID");
    }


    @Test
    public void deleteCommandTest() {

        commandService.deleteCommand(new Long(10));
        verify(commandRepository, times(1)).deleteById(new Long(10));
    }

    @Test
    public void getUserCommandsTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        List<Command> commands= new ArrayList<>();
        commands.add(new Command(new Long(10),formatter.parse("2008-01-17 19:30:59" ), false,
                new User(new Long(1))));

        when(this.commandRepository.getCommandsByUserId(anyLong())).thenReturn(commands);
        assertTrue(this.commandService.getUserCommands(commands.get(0).getUser().getId()).size() == commands.size(),
                "Test failed: Size of list isn't equal to the size of the present test");
    }

    @Test
    public void windUpCommandTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10),formatter.parse("2008-01-17 19:30:59" ), true,
                new User(new Long(1)));
        commandService.windUpCommand(new Long(10));
        verify(commandRepository, times(1)).save(c);
    }

    @Test
    public void getTotalPriceTest() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Command c = new Command(new Long(10),formatter.parse("2008-01-17 19:30:59" ), true,
                new User(new Long(1)));

        commandService.getTotalPrice(new Long(c.getId()));
        verify(commandRepository, times(1)).findById(c.getId());
    }
}