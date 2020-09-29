package com.libraryService.repository;

import com.libraryService.model.BookIssued;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookIssuedRepo extends JpaRepository<BookIssued, Long> {

    BookIssued findByUseridAndBookid(Long userid, Long bookid);
}
