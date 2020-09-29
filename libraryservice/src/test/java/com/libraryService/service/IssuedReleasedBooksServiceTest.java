package com.libraryService.service;

import com.libraryService.dto.BookIssuedDTO;
import com.libraryService.repository.BookIssuedRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static com.libraryService.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class IssuedReleasedBooksServiceTest {

    private static final long USER_ID = 1;
    private static final long BOOK_ID = 1;

    @Mock
    private BookIssuedRepo bookIssuedRepo;
    private BookIssuedService bookIssuedService;

    @Before
    public void setUp() {
        bookIssuedService = new BookIssuedService( bookIssuedRepo );
    }

    @Test
    public void issueBookWillIssueBook() {
        BookIssuedDTO testIssuedDTO = getTestIssuedDTO();
        given( bookIssuedRepo.save(any())).willReturn(toIssuedEntity(testIssuedDTO));
        Optional<BookIssuedDTO> issuedDTOOptional = Optional.ofNullable( this.bookIssuedService.issueBook( USER_ID, BOOK_ID ) );
        assertThat(issuedDTOOptional).isNotEmpty();
        if (issuedDTOOptional.isPresent()) {
            BookIssuedDTO issuedDTO = issuedDTOOptional.get();
            assertThat(issuedDTO.getId()).isEqualTo(testIssuedDTO.getId());
            compareTwoIssuedDTOExcludingId(issuedDTO, testIssuedDTO);
        }
    }

    @Test
    public void releaseBook(){
        BookIssuedDTO testBookIssuedDTO = getTestIssuedDTO();
        given( bookIssuedRepo.findByUseridAndBookid(anyLong(), anyLong())).willReturn(toIssuedEntity(testBookIssuedDTO));
        bookIssuedService.releaseBook( USER_ID, BOOK_ID );
    }
}
