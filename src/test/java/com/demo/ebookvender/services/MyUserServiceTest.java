package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.User;
import com.demo.ebookvender.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class MyUserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    MyUserService userService;

    @BeforeEach
    public void setUp() {
        System.out.println("The test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("The test ended");
    }

    @Test
    public void getUserByIdTest() {
        User u = new User(new Long(5), "hamzaz", "hamzaz", "ROLE_USER", true);
        when(this.userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(u));
        assertEquals(5, u.getId());
        assertSame(this.userService.getUserById(u.getId()).getId(), u.getId(),
                "Error");
    }

    @Test
    public void getAllUsersTest() {
        List<User> u = new ArrayList<>();
        u.add(new User(new Long(1), "admin", "admin", "ROLE_ADMIN,ROLE_USER", true));
        u.add(new User(new Long(3), "hamzaz", "hamzaz", "ROLE_USER", true));
        u.add(new User(new Long(4), "hamzaz2", "hamzaz2", "ROLE_ADMIN", true));
        u.add(new User(new Long(5), "hamzaz3", "hamzaz3", "ROLE_USER", true));

        when(this.userRepository.findAll()).thenReturn(u);
        assertTrue(this.userService.getAllUsers().size() == u.size(),
                "Error");
    }

    @Test
    public void addUserTest() throws Exception {
        User u = new User("hamzaz", "hamzaz", "ROLE_USER", true);
        userService.addUser(u);
        verify(userRepository, times(1)).save(u);
    }

    @Test
    public void addAdminTest() throws Exception {

        User u = new User("hamzaz", "hamzaz", "ROLE_ADMIN", true);
        userService.addAdmin(u);
        verify(userRepository, times(1)).save(u);
    }

    @Test
    public void deleteUserTest() {
        userService.deleteUser(new Long(5));
        verify(userRepository, times(1)).deleteById(new Long(5));
    }
}