package com.demo.ebookvender.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommandLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "The quantity must be at least 1")
    @NotNull
    private int quantity;
    @ManyToOne
    private Book book;
    @ManyToOne
    private Command command;

    public CommandLine(@Positive(message = "The quantity must be at least 1") @NotNull int quantity) {
        this.quantity = quantity;
    }
}
