package com.vermeg.bookstore.security;

import com.vermeg.bookstore.entities.User;
import com.vermeg.bookstore.repositories.UserRepository;
import com.vermeg.bookstore.services.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(getUserDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/bookstore/").permitAll()
                .antMatchers("/bookstore/book/{id}").permitAll()
                .antMatchers("/bookstore/book/**").hasRole("ADMIN")
                .antMatchers("/command/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/command-line/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/user/add").permitAll()
                .antMatchers("/admin/add").hasRole("ADMIN")
                .antMatchers("/user/{id}/delete").hasRole("ADMIN")
                .and().httpBasic();
    }


    @Bean
    public UserDetailsService getUserDetailsService() {
        return new MyUserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // If the User table is empty it will be initialized with a user
    @PostConstruct
    public void init(){
        if (userRepository.findAll().size()==0){
            User user = new User();
            user.setActive(true);
            user.setEmail("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles("ROLE_ADMIN,ROLE_USER");
            userRepository.save(user);
        }
    }
}
