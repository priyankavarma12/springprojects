package com.bookService.exception;

import com.bookService.dto.ErrorDTO;
import com.bookService.utils.ControllersUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handling Book Not Found Exception. This exception is thrown when the book
     * of a particular id is not found in the system
     *
     * @return ResponseEntity with not value and status as 404
     */
    @ExceptionHandler(BooksNotFoundException.class)
    protected ResponseEntity<Object> bookNotFoundHandler(
            BooksNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNotFoundResponseEntity();
    }

    /**
     * Handling No Books Found Exception. This exception is thrown when there are
     * no books found in the system
     *
     * @return ResponseEntity with not value and status as 204
     */
    @ExceptionHandler(NoBooksFoundException.class)
    protected ResponseEntity<Object> noBooksFoundHandler(
            NoBooksFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getNoContentResponseEntity();
    }

    /**
     * Handling Invalid Book Data Exception. This exception is thrown when there are
     * required fields missing in Book Model
     *
     * @return ResponseEntity with Error DTO and status as 500
     */
    @ExceptionHandler(InvalidBookDataException.class)
    protected ResponseEntity<ErrorDTO> invalidBookDataHandler(InvalidBookDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getInternalServerErrorResponseEntity(ex);
    }

    /**
     * Handling Invalid Book Data Exception. This exception is thrown when there are
     * required fields missing in Book Model
     *
     * @return ResponseEntity with Error DTO and status as 500
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDTO> invalidBookDataHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtil.getInternalServerErrorResponseEntity(ex);
    }

}
