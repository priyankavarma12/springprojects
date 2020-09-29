package com.libraryService.util;

import com.libraryService.dto.ErrorDTO;

public class Utils {

    private Utils() {
        throw new IllegalStateException( "Utility class" );
    }

    /**
     * Helper method for creating the {@link ErrorDTO} from {@link Exception}
     *
     * @param ex Exception
     * @return {@link ErrorDTO}
     */
    static ErrorDTO getErrorDtoFromException(Exception ex) {
        return ErrorDTO.builder()
                .exception( ex.getClass().getSimpleName() )
                .message( ex.getMessage() )
                .build();
    }

}
