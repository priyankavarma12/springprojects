package com.libraryService.client;

import com.libraryService.client.fallback.UserClientFallBack;
import com.libraryService.dto.UserDTO;
import com.libraryService.model.UserResponseBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", fallback = UserClientFallBack.class)
public interface UserClient {

    @PostMapping("/users")
    ResponseEntity<UserDTO> createUser( @RequestBody UserDTO user);

    @GetMapping("/users/{user_id}")
    ResponseEntity<UserDTO> getUserById(@PathVariable("user_id") Long id);

    @GetMapping("/users")
    ResponseEntity<List<UserDTO>> getAllUsers();

    @PutMapping("/users/{user_id}")
    ResponseEntity<UserResponseBody> updateUser(@PathVariable("user_id") Long id, @RequestBody UserDTO user);

    @DeleteMapping("/users/{user_id}")
    ResponseEntity<UserResponseBody> deleteUser(@PathVariable("user_id") Long id);

}
