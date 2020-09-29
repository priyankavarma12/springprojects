package com.libraryService.controller;

import com.libraryService.client.BookClient;
import com.libraryService.client.UserClient;
import com.libraryService.constants.StringConstants;
import com.libraryService.dto.BookDTO;
import com.libraryService.dto.BookIssuedDTO;
import com.libraryService.dto.UserDTO;
import com.libraryService.exception.BooksNotFoundException;
import com.libraryService.exception.NoBooksFoundException;
import com.libraryService.exception.NoUsersFoundException;
import com.libraryService.model.UserResponseBody;
import com.libraryService.service.BookIssuedService;
import com.libraryService.util.ControllersUtil;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Library controller.
 */
@Slf4j
@RestController
@RequestMapping(value = "/library")
@Api(tags = {StringConstants.LIBRARY_SWAGGER_TAG})
@RequiredArgsConstructor
public class LibraryController {

    private final BookClient bookClient;

    private final UserClient userClient;

    private final BookIssuedService bookIssuedService;

    /**
     * Get all available books in the system
     *
     * @return List of BookDTOs {@link UserDTO}
     */
    @ApiOperation(value = "View list of available books", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No books present in the database")
    }
    )
    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        ResponseEntity<List<BookDTO>> allBooks = bookClient.getAllBooks();
        if (null != allBooks) {
            return allBooks;
        } else {
            throw new NoBooksFoundException();
        }
    }

    /**
     * Get an book from book id
     *
     * @param bookId the book id
     * @return Found BookDTO {@link UserDTO}
     */
    @ApiOperation(value = "Search an book with an ID", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved book"),
            @ApiResponse(code = 404, message = "No book present in the database with the given ID")
    }
    )
    @GetMapping(value = "/books/{book_id}")
    public ResponseEntity<BookDTO> getBook(@ApiParam(value = "Book's ID that need to be fetched", required = true, example = "111") @PathVariable("book_id") Long bookId) {
        ResponseEntity<BookDTO> book = bookClient.getBook( bookId );
        if (null != book) {
            return book;
        } else {
            throw new BooksNotFoundException();
        }
    }

    /**
     * Add a new book
     *
     * @param book {@link UserDTO} model
     * @return Added BookDTO {@link UserDTO}
     */
    @ApiOperation(value = "Add a new book", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@ApiParam(value = "Book model that needs to be added", required = true, example = "Book Added") @Valid @RequestBody BookDTO book) {
        ResponseEntity<BookDTO> savedBookDTO = bookClient.addBook( book );
        if (null != savedBookDTO) {
            return savedBookDTO;
        } else {
            return ControllersUtil.getInternalServerErrorResponseEntity();
        }
    }

    /**
     * Update an book using its book id
     *
     * @param bookId and updated book {@link UserDTO} model
     * @param book   the book
     * @return Updated BookDTO {@link UserDTO}
     */
    @ApiOperation(value = "Update an book with an ID", response = BookDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @PutMapping(value = "/books/{book_id}")
    public ResponseEntity<BookDTO> updateBook(@ApiParam(value = "Book's ID that need to be updated", required = true, example = "123") @PathVariable("book_id") Long bookId,
                                              @ApiParam(value = "Book model that needs to be updated", required = true, example = "Updated Book Model") @Valid @RequestBody BookDTO book) {
        ResponseEntity<BookDTO> updatedBookDTO = bookClient.updateBook( bookId, book );
        if (null != updatedBookDTO) {
            return updatedBookDTO;
        } else {
            return ControllersUtil.getInternalServerErrorResponseEntity();
        }
    }

    /**
     * Delete an book using its book id
     *
     * @param bookId the book id
     * @return HttpStatus 200 on Successful Delete
     */
    @ApiOperation(value = "Delete an book with an ID", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted book"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @DeleteMapping(value = "/books/{book_id}")
    public HttpStatus deleteBook(@ApiParam(value = "Book's ID that need to be deleted", required = true, example = "124") @PathVariable("book_id") Long bookId) {
         return bookClient.deleteBook( bookId );
    }


    /**
     * Gets all users.
     *
     * @return the all users
     */
    @ApiOperation(value = "View list of available users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No users present in the database")
    }
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        ResponseEntity<List<UserDTO>> allUsers = userClient.getAllUsers();
        if (null != allUsers) {
            return allUsers;
        } else {
            throw new NoUsersFoundException();
        }
    }


    /**
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    @ApiOperation(value = "Search a User with id", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No users present in the database")
    }
    )
    @GetMapping(value = "/users/{user_id}")
    public ResponseEntity<UserDTO> getUserById(@ApiParam(value = "Users's ID that need to be fetched", required = true, example = "121") @PathVariable("user_id") Long userId) {
        ResponseEntity<UserDTO> user = userClient.getUserById( userId );
        if (null != user) {
            return user;
        } else {
            throw new NoUsersFoundException();
        }
    }


    /**
     * Create user response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @ApiOperation(value = "Add a users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No users present in the database")
    }
    )
    @PostMapping(value = "/users")
    public ResponseEntity<UserDTO> createUser(@ApiParam(value = "User model that needs to be added", required = true, example = "create user") @Valid @RequestBody UserDTO user) {
        ResponseEntity<UserDTO> addedUser = userClient.createUser( user );
        if (null != addedUser) {
            return addedUser;
        } else {
            return ControllersUtil.getInternalServerErrorResponseEntity();
        }
    }

    /**
     * Update user response entity.
     *
     * @param userId the user id
     * @param user   the user
     * @return the response entity
     */
    @ApiOperation(value = "Update an User with an ID", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @PutMapping(value = "/users/{user_id}")
    public ResponseEntity<UserResponseBody> updateUser(@ApiParam(value = "User's ID that need to be updated", required = true, example = "121") @PathVariable("user_id") Long userId,
                                                       @ApiParam(value = "User model that needs to be updated", required = true, example = "updated user model") @Valid @RequestBody UserDTO user) {
        ResponseEntity<UserResponseBody> updatedUser = userClient.updateUser(  userId, user );
        if (null != updatedUser) {
            return updatedUser;
        } else {
            return ControllersUtil.getInternalServerErrorResponseEntity();
        }
    }


    /**
     * Delete user response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @ApiOperation(value = "Delete an user with an ID", response = HttpStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user"),
            @ApiResponse(code = 500, message = "Some error happened during the operation")
    }
    )
    @DeleteMapping(value = "/users/{user_id}")
    public ResponseEntity<UserResponseBody> deleteUser(@ApiParam(value = "Book's ID that need to be deleted", required = true, example = "122") @PathVariable("user_id") Long userId) {
        return userClient.deleteUser( userId );
    }

    /**
     * Issue books response entity.
     *
     * @param userid the userid
     * @param bookid the bookid
     * @return the response entity
     */
    @ApiOperation(value = "Issue a books to user", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No users present in the database")
    }
    )
    @PostMapping(value = "/users/{user_id}/books/{book_id}")
    public ResponseEntity<BookIssuedDTO> issueBooks(@ApiParam(value = "User model that needs to be added", required = true, example = "121") @PathVariable("user_id") Long userid, @PathVariable("book_id") Long bookid) {
        if(userid != null && bookid != null) {
            BookIssuedDTO bookIssuedDTO = bookIssuedService.issueBook( userid, bookid );
            if (null != bookIssuedDTO) {
                return new ResponseEntity<>( bookIssuedDTO, HttpStatus.CREATED );
            } else {
                return ControllersUtil.getInternalServerErrorResponseEntity();
            }
        }
        return ControllersUtil.getNotFoundResponseEntity();
    }


    /**
     * Release book http status.
     *
     * @param userid the userid
     * @param bookid the bookid
     * @return the http status
     */
    @ApiOperation(value = "Release a book to users", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "No users present in the database")
    }
    )
    @DeleteMapping(value = "/users/{user_id}/books/{book_id}")
    public HttpStatus releaseBook(@ApiParam(value = "User model that needs to be added", required = true, example = "121") @PathVariable("user_id") Long userid, @PathVariable("book_id") Long bookid) {
        try {
            bookIssuedService.releaseBook( userid, bookid );
            return HttpStatus.OK;
        } catch (Exception ex) {
            log.error( ex.getLocalizedMessage());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
