package com.usersservice.service;

import com.usersservice.dao.UsersRepository;
import com.usersservice.dto.UserDTO;
import com.usersservice.entity.Users;
import com.usersservice.util.Utils;
import com.usersservice.utils.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UsersServiceImpl usersServiceImpl;

    @MockBean
    private UsersRepository usersRepository;

    @Test
    public void testCreateUsers() {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 1L );
        logger.info("User created test data : {} ", userData);
        Mockito.when(usersRepository.save( Utils.convertToUsersEntity(userData))).thenReturn(Utils.convertToUsersEntity(userData));
        assertThat(usersServiceImpl.create(userData)).isEqualTo(userData);

        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.create(userData);
        verify(mockObj, times(1)).create(userData);

    }

    @Test
    public void testCreateUsersInvalidInput()  {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 1L );
        userData.setUsername( "" );
        logger.info("Users created test data : {} ", userData);
        Mockito.when(usersRepository.save(Utils.convertToUsersEntity(userData))).thenReturn(Utils.convertToUsersEntity(userData));
        assertThat(usersServiceImpl.create(userData)).isEqualTo(userData);
        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.create(userData);
        verify(mockObj, times(1)).create(userData);
    }

    @Test
    public void testGetBooksById(){
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 1L );
        logger.info("Get Users by id test data : {}", userData);
        Mockito.when(usersRepository.findById((long)1)).thenReturn( Optional.of( Utils.convertToUsersEntity( userData ) ) );
        assertThat(usersServiceImpl.getUsersById((long)1)).isEqualTo(userData);
        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.getUsersById(userData.getUserid());
        verify(mockObj, times(1)).getUsersById((long)1);
    }

    @Test
    public void testGetAllBooks(){
        UserDTO userData = TestUtil.getTestUser();
        List<UserDTO> users = new ArrayList<>(  );
        users.add( userData );
        logger.info("Get Users  test data : {}", users);
        Mockito.when(usersRepository.findAll()).thenReturn( users.stream().map( Utils::convertToUsersEntity ).collect( Collectors.toList()) ) ;
        List<UserDTO> allUsers = usersServiceImpl.getAllUsers();
        assertThat( allUsers.size() ).isEqualTo( users.size() );
        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.getAllUsers();
        verify(mockObj, atLeast(1)).getAllUsers();
    }

    @Test
    public void testUpdateBooks(){
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 1L );
        Mockito.when(usersRepository.findById((long)1)).thenReturn( Optional.of( Utils.convertToUsersEntity( userData ) ) );
        userData.setAddress("Rolls Ralph");
        Mockito.when(usersRepository.save(Utils.convertToUsersEntity(userData))).thenReturn(Utils.convertToUsersEntity(userData));
        assertThat(usersServiceImpl.update((long)1, userData)).isEqualTo(userData);
        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.update(userData.getUserid(), userData);
        verify(mockObj, atLeast(1)).update((long)1, userData);
    }

    @Test
    public void testForDeleteBooks() {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 123L );
        logger.info("Delete Users by id : {} ", userData);
        Mockito.when(usersRepository.findById((long)1)).thenReturn( Optional.of( Utils.convertToUsersEntity( userData ) ) );
        Mockito.when(usersRepository.existsById(userData.getUserid())).thenReturn(false);
        assertFalse(usersRepository.existsById(userData.getUserid()));
        UsersServiceImpl mockObj = Mockito.mock(UsersServiceImpl.class);
        mockObj.delete(userData.getUserid());
        verify(mockObj, atLeast(1)).delete((long)123);
    }

}
