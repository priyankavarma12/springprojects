package com.libraryService.client.fallback;

import com.libraryService.client.BookClient;
import com.libraryService.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class BookClientFallBack implements BookClient {


    @Override
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.of(
                Optional.of(
                        Arrays.asList(BookDTO.builder()
                                .author("Dummy")
                                .id(1010101L)
                                .description("Dummy")
                                .build())));
    }

    @Override
    public ResponseEntity<BookDTO> addBook(BookDTO bookDTO) {
        return ResponseEntity.ok(BookDTO.builder()
                .author("Dummy")
                .id(1010101L)
                .description("Dummy")
                .build());
    }

    @Override
    public ResponseEntity<BookDTO> getBook(Long bookId) {
        return ResponseEntity.ok(BookDTO.builder()
                .author("Dummy")
                .id(1010101L)
                .description("Dummy")
                .build());
    }

    @Override
    public ResponseEntity<BookDTO> updateBook(Long bookId, BookDTO book) {
        return ResponseEntity.ok(BookDTO.builder()
                .author("Dummy")
                .id(1010101L)
                .description("Dummy")
                .build());
    }

    @Override
    public HttpStatus deleteBook(Long bookId) {
        return HttpStatus.OK;
    }
}
