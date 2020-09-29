package com.libraryService.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryService.dto.BookDTO;
import com.libraryService.dto.BookIssuedDTO;
import com.libraryService.model.BookIssued;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    public static BookIssuedDTO getTestIssuedDTO() {
        return BookIssuedDTO .builder()
                .userid(1L)
                .bookid(1L)
                .build();
    }

    public static void compareTwoIssuedDTOExcludingId(BookIssuedDTO issuedDTO, BookIssuedDTO testIssuedDTO) {
        assertThat(issuedDTO.getBookid()).isEqualTo(testIssuedDTO.getBookid());
        assertThat(issuedDTO.getUserid()).isEqualTo(testIssuedDTO.getUserid());
    }

    public static Optional<BookIssuedDTO> toIssuedDTO(BookIssued issued) {
        if (null == issued) {
            return Optional.empty();
        }
        return Optional.of(BookIssuedDTO.builder()
                .bookid(issued.getBookid())
                .userid(issued.getUserid())
                .id(issued.getId())
                .build());
    }
    public static BookIssued toIssuedEntity(BookIssuedDTO issued) {
        return BookIssued.builder()
                .bookid(issued.getBookid())
                .userid(issued.getUserid())
                .id(issued.getId())
                .build();
    }

}
