package com.epam.atithi.util;

import com.epam.atithi.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Utility class for Controllers. It can be used for returning
 * different types of Response Entities
 *
 * @author Spallya Omar
 */
public class ControllersUtils {

    private ControllersUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type <T>
     * with {@link HttpStatus} INTERNAL_SERVER_ERROR (500)
     *
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<T> getInternalServerErrorResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type {@link ErrorDTO}
     * with {@link HttpStatus} INTERNAL_SERVER_ERROR (500)
     *
     * @param ex Exception
     * @return {@link ResponseEntity}
     */
    public static ResponseEntity<ErrorDTO> getInternalServerErrorResponseEntity(Exception ex) {
        return new ResponseEntity<>(ConverterUtils.getErrorDtoFromException(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type <T>
     * with {@link HttpStatus} OK (200)
     *
     * @param <T> Type of the value
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<T> getOkResponseEntity(T returnObject) {
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type <T>
     * with {@link HttpStatus} CREATED (201)
     *
     * @param <T> Type of the value
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<T> getCreatedResponseEntity(T returnObject) {
        return new ResponseEntity<>(returnObject, HttpStatus.CREATED);
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type <T>
     * with {@link HttpStatus} NOT_FOUND (404)
     *
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<T> getNotFoundResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ErrorDTO> getNotFoundResponseEntity(Exception ex) {
        return new ResponseEntity<>(ConverterUtils.getErrorDtoFromException(ex), HttpStatus.NOT_FOUND);
    }

    /**
     * Helper method for returning the {@link ResponseEntity} of type <T>
     * with {@link HttpStatus} NO_CONTENT (204)
     *
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<T> getNoContentResponseEntity() {
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }




}
