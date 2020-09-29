package com.bookService.service;

import com.bookService.dao.BooksRepository;
import com.bookService.dto.BookDTO;
import com.bookService.entity.Books;
import com.bookService.exception.BooksNotFoundException;
import com.bookService.exception.NoBooksFoundException;
import com.bookService.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;

    @Override
    public Optional<BookDTO> save(BookDTO bookDTO) {
        try {
            return Optional.ofNullable( Utils.mapBookEntityToDTO( this.booksRepository.save( Utils.convertToBookEntity( bookDTO ) ) ) );
        } catch (Exception ex) {
            log.error( ex.getLocalizedMessage() );
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDTO> findById (Long bookId) {
        Optional<Books> foundBook = Optional.empty();
        try {
            foundBook = this.booksRepository.findById(bookId);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        if(!foundBook.isPresent()) {
            throw new BooksNotFoundException("Book with id: "
                    + bookId + " is not found in the system");
        }
        return Optional.ofNullable( Utils.mapBookEntityToDTO( foundBook.get() ) );
    }

    @Override
    public Optional<BookDTO> updateById(Long bookId, BookDTO updatedBookDTO){
        Optional<Books> foundBook = Optional.empty();
        try {
            if(null != bookId) {
                foundBook = booksRepository.findById( bookId );
            }
            if(foundBook.isPresent()) {
                Books updatedBooks = Utils.convertToBookEntity( updatedBookDTO );
                updatedBooks.setId( foundBook.get().getId() );
                this.booksRepository.save( updatedBooks );
                return Optional.ofNullable( Utils.mapBookEntityToDTO( updatedBooks ) );
            }
        } catch (Exception ex){
            log.error( ex.getLocalizedMessage() );
        }
        return Optional.empty();
    }

    @Override
    public List<BookDTO> findAll(){
        List<Books> allBooks = Collections.emptyList();
        try {
            allBooks = (List<Books>) this.booksRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        if (null == allBooks || allBooks.isEmpty()) {
            throw new NoBooksFoundException("No books found in the database");
        }
        return allBooks.stream().map(Utils::mapBookEntityToDTO).collect( toList() );
    }

    @Override
    public void deleteById(Long bookId) {
        try {
            this.booksRepository.deleteById(bookId);
        } catch (EmptyResultDataAccessException ex) {
            log.error(ex.getLocalizedMessage());
            throw new BooksNotFoundException("Book with id: "
                    + bookId + " is not found in the system");
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
    }

}
