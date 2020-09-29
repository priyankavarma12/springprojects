package com.bookService.utils;

import com.bookService.dto.BookDTO;
import com.bookService.dto.ErrorDTO;
import com.bookService.entity.Books;
import org.apache.commons.lang3.StringUtils;

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
    public static ErrorDTO getErrorDtoFromException(Exception ex) {
        return ErrorDTO.builder()
                .exception( ex.getClass().getSimpleName() )
                .message( ex.getMessage() )
                .build();
    }

    /**
     * Helper method for validating the Book data
     *
     * @param books {@link Books}
     * @return {@link ErrorDTO}
     */
    public static boolean isBookDataValid(Books books) {
        boolean isValid = false;
        if (StringUtils.isNoneBlank( books.getName(), books.getAuthor(), books.getPublishedyear() )
                && books.getPrice() != null && books.getPrice() > 0) {
            isValid = true;
        }
        return isValid;
    }

    public static BookDTO mapBookEntityToDTO(Books books) {
        if (null == books) {
            return null;
        }
        return BookDTO.builder()
                .id( books.getId() )
                .name( books.getName() )
                .author( books.getAuthor() )
                .description( books.getDescription() )
                .genre( books.getGenre() )
                .price( books.getPrice() )
                .publishedyear( books.getPublishedyear() )
                .build();
    }

    public static Books convertToBookEntity(BookDTO bookDTO) {
        return Books.builder()
                .id( bookDTO.getId() )
                .name( bookDTO.getName() )
                .author( bookDTO.getAuthor() )
                .description( bookDTO.getDescription() )
                .genre( bookDTO.getGenre() )
                .price( bookDTO.getPrice() )
                .publishedyear( bookDTO.getPublishedyear() )
                .build();
    }

}
