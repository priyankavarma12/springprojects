package com.bookService.controller;

import com.bookService.dto.BookDTO;
import com.bookService.dto.ErrorDTO;
import com.bookService.exception.BooksNotFoundException;
import com.bookService.exception.NoBooksFoundException;
import com.bookService.service.BooksServiceImpl;
import com.bookService.utils.ControllersUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Slf4j
@Api(value = "This is simple Book Service Management System. We can use this api to add, update, delete and get details of books")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully add new Book"),
        @ApiResponse(code = 201, message = "Created new book"),
        @ApiResponse(code = 204, message = "No Content found"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found", response = ErrorDTO.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDTO.class)})
public class BookController {

    @Autowired
    private BooksServiceImpl booksService;

    @PostMapping
    @ApiOperation(value = "Add new book to service ", response = BookDTO.class)
    public ResponseEntity<BookDTO> addBook(@ApiParam(value = "Book model that needs to be added", required = true) @Valid @RequestBody BookDTO book) {
        Optional<BookDTO> savedBookDTO = this.booksService.save(book);
        return savedBookDTO.map( ControllersUtil::getCreatedResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }

    @GetMapping("/{book_id}")
    @ApiOperation(value = "Get Book details by id" , response = BookDTO.class)
    public ResponseEntity<BookDTO> getBook(@ApiParam(value = "Book's ID that need to be fetched", required = true) @PathVariable("book_id") Long bookId) {
        Optional<BookDTO> book = this.booksService.findById(bookId);
        return book.map(ControllersUtil::getOkResponseEntity)
                .orElseThrow( BooksNotFoundException::new);
    }

    @GetMapping
    @ApiOperation(value = "Get List of all books", response = BookDTO.class)
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = this.booksService.findAll();
        if (null != books && !books.isEmpty()) {
            return ControllersUtil.getOkResponseEntity(books);
        } else {
            throw new NoBooksFoundException();
        }
    }

    @PutMapping("/{book_id}")
    @ApiOperation(value = "Update Book by id", response = BookDTO.class)
    public ResponseEntity<BookDTO> updateBook(@ApiParam(value = "Book's ID that need to be updated", required = true) @PathVariable("book_id") Long bookId,
                                              @ApiParam(value = "Book model that needs to be updated", required = true) @Valid @RequestBody BookDTO book) {
        Optional<BookDTO> updatedBookDTO = this.booksService.updateById(bookId, book);
        return updatedBookDTO.map(ControllersUtil::getOkResponseEntity)
                .orElseGet(ControllersUtil::getInternalServerErrorResponseEntity);
    }


    @DeleteMapping("/{book_id}")
    @ApiOperation(value = "Delete Books by id")
    public HttpStatus deleteBook(@ApiParam(value = "Book's ID that need to be deleted", required = true) @PathVariable("book_id") Long bookId) {
        if (null != bookId) {
            this.booksService.deleteById(bookId);
            return HttpStatus.OK;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
