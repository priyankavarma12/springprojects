package com.usersservice.util;

import com.usersservice.dto.UserDTO;
import com.usersservice.entity.Users;

public class Utils {

    public static UserDTO mapUserEntityToDTO(Users users) {
        if (null == users) {
            return null;
        }
        return UserDTO.builder()
                .userid( users.getUserid() )
                .username( users.getUsername() )
                .address( users.getAddress() )
                .phoneno( users.getPhoneno() )
                .build();
    }

    public static Users convertToUsersEntity(UserDTO userDTO) {
        return Users.builder()
                .userid( userDTO.getUserid() )
                .username( userDTO.getUsername() )
                .address( userDTO.getAddress() )
                .phoneno( userDTO.getPhoneno() )
                .build();

    }

}
