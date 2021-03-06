package com.bookService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Books {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String author;

    private String genre;

    private String description;

    private Double price;

    private String publishedyear;

}
