package com.demo.ebookvender.services;

import com.demo.ebookvender.entities.Command;
import com.demo.ebookvender.entities.MyUserDetails;
import com.demo.ebookvender.entities.User;
import com.demo.ebookvender.repositories.UserRepository;
import com.demo.ebookvender.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfig securityConfig;

    @Autowired
    CommandService commandService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("No user with the username " + email));
        return new MyUserDetails(user.get());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No " +
                "user found with the ID " + id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) throws Exception {
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) throw new Exception("This " +
                "username " +
                "already exist");
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        user.setActive(true);
        return userRepository.save(user);
    }

    public User addAdmin(User user) throws Exception {
        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) throw new Exception("This username " +
                "already exist");
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        user.setRoles("ROLE_ADMIN");
        user.setActive(true);
        return userRepository.save(user);
    }

    public User deleteUser(Long userId) {
        User utl =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(
                        "No user found with a matching ID: " + userId));
        List<Command> c = commandService.getUserCommands(userId);
        for (Command com : c)
            commandService.deleteCommand(com.getId());
        userRepository.deleteById(userId);
        return utl;
    }

    public User updateUserName(Long userId, User user) {
        User u = getUserById(userId);
        u.setEmail(user.getEmail());
        return userRepository.save(u);
    }


}
