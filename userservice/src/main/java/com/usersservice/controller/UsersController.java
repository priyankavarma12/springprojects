package com.usersservice.controller;

import com.usersservice.dto.UserDTO;
import com.usersservice.entity.Constants;
import com.usersservice.exception.ErrorDetails;
import com.usersservice.exception.UsersResponseBody;
import com.usersservice.service.UsersServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "This is simple User Service Management System. We can use this api to add, update, delete and get details of UserDTO")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully add new User"),
        @ApiResponse(code = 201, message = "Created new user"),
        @ApiResponse(code = 204, message = "No Content found"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found", response = ErrorDetails.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorDetails.class)})
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping
    @ApiOperation(value = "Add new user to service ", response = UserDTO.class)
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO user) {
        UserDTO response =  usersService.create(user);
        if(response == null) {
            logger.debug("Users Not Created  : {} ", user);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Created Users  : {} ", user);
        return  ResponseEntity.created(URI.create( Constants.FORWARD_SLASH + Constants.USERS)).body(response);
    }

    @GetMapping("/{user_id}")
    @ApiOperation(value = "Get User details by id" , response = UserDTO.class)
    public ResponseEntity<UserDTO> getUserById(@PathVariable("user_id") Long id) {
        UserDTO response = usersService.getUsersById( id);
        if(response == null) {
            logger.debug("User with id: {} ",id ,"does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Found User {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation(value = "Get List of all UserDTO", response = UserDTO.class)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = usersService.getAllUsers();
        if(userList.isEmpty()) {
            logger.debug("UserDTO does not exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found  "+ userList.size()+ " Users");
        logger.debug(Arrays.toString(userList.toArray()));
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/{user_id}")
    @ApiOperation(value = "Update User by id", response = UsersResponseBody.class)
    public ResponseEntity<UsersResponseBody> updateUser(@Valid @PathVariable("user_id") Long id, @RequestBody UserDTO user) {
        logger.info("Get User with id : {}, {}", id, user);
        UserDTO existingBook = usersService.getUsersById(id);
        if(existingBook == null) {
            logger.debug("User with id "+ id + "does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("Update user with id : {}, {}", id, user);
        usersService.update(id, user);
        final UsersResponseBody usersResponseBody = new UsersResponseBody();
        usersResponseBody.setMessage("User Updated Successfully");
        usersResponseBody.setStatus(Constants.STATUS_SUCCESS);
        return ResponseEntity.ok(usersResponseBody);
    }

    @DeleteMapping("/{user_id}")
    @ApiOperation(value = "Delete User by id", response = UsersResponseBody.class)
    public ResponseEntity<UsersResponseBody> deleteUser(@PathVariable("user_id") Long id) {
        logger.info("Deleted UserDTO with id : {}", id);
        UserDTO user = usersService.getUsersById(id);
        if(user == null) {
            logger.debug("UserDTO with id "+ id + "does not exists");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        usersService.delete(id);
        final UsersResponseBody usersResponseBody = new UsersResponseBody();
        usersResponseBody.setMessage("User Deleted Successfully");
        usersResponseBody.setStatus(Constants.STATUS_SUCCESS);
        return ResponseEntity.ok(usersResponseBody);
    }

}
