package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.Book;
import com.demo.ebookvender.entities.Command;
import com.demo.ebookvender.entities.CommandLine;
import com.demo.ebookvender.repositories.CommandLineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CommandLineServiceTest {

    @Mock
    CommandLineRepository lineRepository;

    @InjectMocks
    CommandLineService lineService;

    @BeforeEach
    public void setUp() {
        System.out.println("The test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("The test ended");
    }

    @Test
    public void getAllCommandLinesTest() {
        List<CommandLine> lines = new ArrayList<>();
        lines.add(new CommandLine(new Long(8), 50, new Book(), new Command()));
        lines.add(new CommandLine(new Long(9), 40, new Book(), new Command()));
        lines.add(new CommandLine(new Long(11), 30, new Book(), new Command()));
        lines.add(new CommandLine(new Long(12), 13, new Book(), new Command()));
        when(this.lineRepository.findAll()).thenReturn(lines);
        assertEquals(lines.size(), this.lineService.getAllCommandLines().size(),
                "Error");
    }

    @Test
    public void getCommandLineByIdTest() {
        CommandLine l = new CommandLine(new Long(8), 10, new Book(), new Command());
        when(lineRepository.findById(l.getId())).thenReturn(java.util.Optional.of(l));
        assertEquals(8, l.getId());
        assertSame(this.lineService.getCommandLineById(l.getId()).getId(), l.getId(),
                "Error");
    }

    @Test
    public void addCommandLineTest() throws Exception {
        CommandLine l = new CommandLine(50);
        lineService.addCommandLine(new Long(1), l, new Long(50));
        verify(lineRepository, times(1)).save(l);
    }

    @Test
    public void deleteCommandLineTest() throws Exception {
        List<CommandLine> lines = new ArrayList<>();
        lines.add(new CommandLine(new Long(8), 10, new Book(), new Command()));
        lines.add(new CommandLine(new Long(9), 2, new Book(), new Command()));
        lines.add(new CommandLine(new Long(11), 2, new Book(), new Command()));
        lines.add(new CommandLine(new Long(12), 4, new Book(), new Command()));
        lineService.deleteCommandLine(lines.get(3).getId());
        verify(lineRepository, times(1)).deleteById(new Long(12));
    }

    @Test
    public void updateComTest() throws Exception {
        List<CommandLine> lines = new ArrayList<>();
        lines.add(new CommandLine(new Long(8), 10, new Book(), new Command()));
        lines.add(new CommandLine(new Long(9), 2, new Book(), new Command()));
        lines.add(new CommandLine(new Long(11), 2, new Book(), new Command()));
        lines.add(new CommandLine(new Long(12), 4, new Book(), new Command()));
        lineService.updateCom(new Long(13), new Long(1), new CommandLine(3));
        verify(lineRepository, times(1)).save(new CommandLine(3));
    }
}