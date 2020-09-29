package com.usersservice.dao;

import com.usersservice.entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UsersRepository extends CrudRepository<Users, Long> {

}
