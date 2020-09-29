package com.usersservice.service;

import com.usersservice.dto.UserDTO;

import java.util.List;

public interface UsersService {

    UserDTO create(UserDTO users);

    List<UserDTO> getAllUsers();

    UserDTO getUsersById(Long id);

    UserDTO update(Long id, UserDTO user);

    void delete(Long id);

}
