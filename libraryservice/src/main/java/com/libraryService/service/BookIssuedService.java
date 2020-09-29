package com.libraryService.service;

import com.libraryService.dto.BookIssuedDTO;
import com.libraryService.model.BookIssued;
import com.libraryService.repository.BookIssuedRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookIssuedService {

    private final BookIssuedRepo bookIssuedRepo;

    public BookIssuedDTO issueBook(Long userId, Long bookId) {
        BookIssued bookIssued = BookIssued.builder().userid( userId ).bookid( bookId ).build();
        BookIssued save = bookIssuedRepo.save( bookIssued );
        return BookIssuedDTO.builder().userid( save.getUserid() ).bookid( save.getBookid() ).id( save.getId() ).build();
    }

    public void releaseBook( Long userId, Long bookId){
        BookIssued bookIssued = bookIssuedRepo.findByUseridAndBookid( userId, bookId );
        if(bookId != null)
            bookIssuedRepo.deleteById( bookIssued.getId() );
        else
            throw new RuntimeException( "Released book not found" );
    }

}
