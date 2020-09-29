package com.libraryService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@Table(name = "book_Issued")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BookIssued {

    @Id
    @GeneratedValue
    private Long id;

    private Long userid;

    private Long bookid;

}
