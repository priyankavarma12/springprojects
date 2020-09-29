package com.usersservice.service;

import com.usersservice.dao.UsersRepository;
import com.usersservice.dto.UserDTO;
import com.usersservice.entity.Users;
import com.usersservice.exception.UsersDetailsNotFoundException;
import com.usersservice.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDTO create(UserDTO users) {
        logger.info("Create Users service : {}", users);
        Users userEntity = Utils.convertToUsersEntity( users );
        return Utils.mapUserEntityToDTO( usersRepository.save(userEntity));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        logger.info("Get All users service ");
        List<Users> userList = (List<Users>) usersRepository.findAll();
        if(userList.size() > 0){
            return userList.stream().map( Utils::mapUserEntityToDTO ).collect( Collectors.toList() );
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public UserDTO getUsersById(Long id) {
        logger.info("Get User by id service : {}", id);
        Optional<Users> usersOpt = usersRepository.findById(id);
        if(!usersOpt.isPresent())
            throw new UsersDetailsNotFoundException("Users by "+ id +" not found");
        else
            return Utils.mapUserEntityToDTO( usersOpt.get() );
    }

    @Override
    public UserDTO update(Long id, UserDTO users) {
        logger.info("Update users service {}", users);
        Optional<UserDTO> usersOpt = Optional.ofNullable(getUsersById(users.getUserid()));
        if(!usersOpt.isPresent())
            throw new UsersDetailsNotFoundException("Users with "+id + "not found");
        else {
            Users updatedUser = Utils.convertToUsersEntity( users );
            updatedUser.setUserid( id );
            return Utils.mapUserEntityToDTO( usersRepository.save( updatedUser ));
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Delete user service by id ");
        Optional<Users> usersOpt = usersRepository.findById(id);
        if(usersOpt.isPresent()) {
            usersRepository.deleteById(id);
        } else {
            throw new UsersDetailsNotFoundException("User Id not found ");
        }
    }
}
