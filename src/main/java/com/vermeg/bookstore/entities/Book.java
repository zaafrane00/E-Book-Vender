package com.vermeg.bookstore.entities;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import javax.validation.constraints.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"title"})})
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "The title can't be empty") @Size(min=4, message = "the title's length " +
            "must be at least 4")
    private String title;
    @NotNull(message = "The author's name can't be empty") @Size(min=4, message = "the author's " +
            "length must be at least 4")
    private String author;
    @NotNull(message = "The price can't be empty") @Positive(message = "The price must be more " +
            "then 0")
    private double price;
    @Nullable
    @PastOrPresent(message = "A valid date" +
            " can't be after the current date")
    private Date releaseDate;

    public Book(String title, String author, double price,  Date releaseDate) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
    public Book(Long id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }
}
