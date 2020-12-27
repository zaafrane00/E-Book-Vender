package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "The username can't be empty") @Size(min=3, message = "the title's length " +
            "must be at least 3")
    private String userName;
    @NotNull(message = "The password can't be empty") @Size(min=4, message = "the password's " +
            "length " +
            "must be at least 8")
    private String password;
    @NotNull
    private String roles;
    private boolean active;

    public User(Long id) {
        this.id = id;
    }

    public User(@NotNull(message = "The username can't be empty") @Size(min = 3, message = "the title's length " +
            "must be at least 3") String userName, @NotNull(message = "The password can't be empty") @Size(min = 4, message = "the password's " +
            "length " +
            "must be at least 8") String password, @NotNull String roles, boolean active) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }
}
