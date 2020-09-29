package com.epam.atithi.controller.advice;

import com.epam.atithi.exception.*;
import com.epam.atithi.dto.ErrorDTO;
import com.epam.atithi.util.ControllersUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * Class Containing Handlers for the Exceptions
 *
 * @author Spallya Omar
 */
@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handling User Not Found Exception. This exception is thrown when the user
     * of a particular id is not found in the system
     *
     * @return ResponseEntity with not value and status as 404
     */
    @ExceptionHandler(VisitorNotFoundException.class)
    protected ResponseEntity<ErrorDTO> visitorNotFoundHandler(VisitorNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getNotFoundResponseEntity(ex);
    }

    /**
     * Handling No Users Found Exception. This exception is thrown when there are
     * no users found in the system
     *
     * @return ResponseEntity with not value and status as 204
     */
    @ExceptionHandler(NoVisitorsFoundForAdminException.class)
    protected ResponseEntity<ErrorDTO> noVisitorsFoundHandler(NoVisitorsFoundForAdminException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getNotFoundResponseEntity(ex);
    }

    /**
     * Handling Invalid User Data Exception. This exception is thrown when there are
     * required fields missing in User Model
     *
     * @return ResponseEntity with Error DTO and status as 500
     */
    @ExceptionHandler(AdminNotFoundException.class)
    protected ResponseEntity<ErrorDTO> adminIdNotFoundExceptionHandler(AdminNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getNotFoundResponseEntity(ex);
    }

    /**
     * Handling Method Argument Not Valid Exception. This exception is thrown when there are
     * required fields missing in User Model from Controller
     *
     * @return ResponseEntity with Error DTO and status as 500
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorDTO> invalidBookDataHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

    @ExceptionHandler(AtithiAuthenticationFailedException.class)
    protected ResponseEntity<ErrorDTO> atithiAuthenticationFailedExceptionHandler(AtithiAuthenticationFailedException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

    @ExceptionHandler(UnableToSaveDataException.class)
    protected ResponseEntity<ErrorDTO> unableToSaveDateExceptionHandler(UnableToSaveDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

    @ExceptionHandler(UnableToUpdateDataException.class)
    protected ResponseEntity<ErrorDTO> unableToUpdateDataExceptionHandler(UnableToUpdateDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

    @ExceptionHandler(UnableToFetchDataException.class)
    protected ResponseEntity<ErrorDTO> unableToFetchDataExceptionHandler(UnableToFetchDataException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

    @ExceptionHandler(QRCodeGenerationException.class)
    protected ResponseEntity<ErrorDTO> unableToGetQRCodeDateExceptionHandler(QRCodeGenerationException ex) {
        log.error(ex.getLocalizedMessage());
        return ControllersUtils.getInternalServerErrorResponseEntity(ex);
    }

}
