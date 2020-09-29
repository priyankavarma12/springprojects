package com.libraryService.client;

import com.libraryService.client.fallback.BookClientFallBack;
import com.libraryService.dto.BookDTO;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "book-service", fallback = BookClientFallBack.class)
public interface BookClient {

    @GetMapping("/books")
    ResponseEntity<List<BookDTO>> getAllBooks();

    @PostMapping("/books")
    ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO);

    @GetMapping("/books/{book_id}")
    ResponseEntity<BookDTO> getBook(@PathVariable("book_id") Long bookId);

    @PutMapping("/books/{book_id}")
    ResponseEntity<BookDTO> updateBook(@PathVariable("book_id") Long bookId, @RequestBody BookDTO book);

    @DeleteMapping("/books/{book_id}")
    HttpStatus deleteBook( @PathVariable("book_id") Long bookId);

}
