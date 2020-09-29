package com.epam.atithi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class used for returning the error information when internal
 * server error happens
 *
 * @author Spallya Omar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    private String exception;
    private String message;

}
