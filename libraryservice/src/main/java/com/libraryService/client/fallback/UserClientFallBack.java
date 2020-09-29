package com.libraryService.client.fallback;

import com.libraryService.client.UserClient;
import com.libraryService.dto.UserDTO;
import com.libraryService.model.UserResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserClientFallBack implements UserClient {

    @Override
    public ResponseEntity<UserDTO> createUser(UserDTO user) {
        return null;
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseBody> updateUser(Long id, UserDTO user) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseBody> deleteUser(Long id) {
        return null;
    }
}
