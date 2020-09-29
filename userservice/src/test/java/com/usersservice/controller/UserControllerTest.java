package com.usersservice.controller;

import com.usersservice.dto.UserDTO;
import com.usersservice.entity.Users;
import com.usersservice.entity.Constants;
import com.usersservice.service.UsersServiceImpl;
import com.google.gson.reflect.TypeToken;
import com.usersservice.utils.TestUtil;
import org.apache.commons.math.stat.inference.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersController.class)
public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersServiceImpl usersServiceImpl;

    @Test
    public void testCreateUsers() throws Exception {
        //Mock Data
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( 1L );
        when(usersServiceImpl.create(any( UserDTO.class))).thenReturn(userData);
        logger.info("Create Users data result : {} ", userData);
        //execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Constants.URL).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(TestUtil.objectToJson(userData))).andReturn();

        // verify
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.CREATED.value(), status);

        // verify that service method was called once
        verify(usersServiceImpl, times(1)).create(any(UserDTO.class));

        Users resultUser = TestUtil.jsonToObject(result.getResponse().getContentAsString(), Users.class);
        assertNotNull(resultUser);
        assertEquals(1L, resultUser.getUserid().longValue());
    }

    @Test
    public void testCreateUsersInvalidInput() throws Exception {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUsername( "" );
        userData.setPhoneno("12345678");
        when(usersServiceImpl.create(any(UserDTO.class))).thenReturn(userData);
        logger.info("Create Users data result : {} ", userData);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(Constants.URL).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(TestUtil.objectToJson(userData))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.BAD_REQUEST.value(), status);
        Users resultUser = TestUtil.jsonToObject(result.getResponse().getContentAsString(), Users.class);
        assertNotNull(resultUser);
    }


    @Test
    public void testGetUsersById() throws Exception {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( (long) 12 );
        when(usersServiceImpl.getUsersById(anyLong())).thenReturn(userData);
        logger.info("Get Users by id result : {} ", userData);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(
                Constants.URL + Constants.FORWARD_SLASH + "{id}", 12).accept(
                MediaType.APPLICATION_JSON)).andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        verify(usersServiceImpl, times(1)).getUsersById(any(Long.class));

        Users resultUser = TestUtil.jsonToObject(result.getResponse().getContentAsString(), Users.class);
        assertNotNull(resultUser);
        assertEquals(12, resultUser.getUserid().intValue());
    }

    @Test
    public void testGetUsersNotExist() throws Exception {
        MvcResult result = mockMvc
                        .perform(MockMvcRequestBuilders.get(Constants.URL + Constants.FORWARD_SLASH + "{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals("Incorrect Response Status", HttpStatus.NOT_FOUND.value(), status);

        verify(usersServiceImpl, times(1)).getUsersById(any(Long.class));

        Users resultUser = TestUtil.jsonToObject(result.getResponse().getContentAsString(), Users.class);
        logger.info("Get Users not exist : {}", resultUser);
        assertNull(resultUser);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDTO> userList = TestUtil.listOfUsers();
        when(usersServiceImpl.getAllUsers()).thenReturn(userList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(Constants.URL).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.OK.value(), status);
        verify(usersServiceImpl, times(1)).getAllUsers();

        // get the List<Users> from the Json response
        TypeToken<List<Users>> token = new TypeToken<List<Users>>() {
        };

        List usersListResult = TestUtil.jsonToList(result.getResponse().getContentAsString(), token);
        logger.info("Get all users data list : {}", usersListResult);
        assertNotNull("Users not found", usersListResult);
        assertEquals("Incorrect Users List", userList.size(), usersListResult.size());
    }

    @Test
    public void testGetAllUsersListIsEmpty() throws Exception {
        List<UserDTO> userList = Collections.emptyList();
        when(usersServiceImpl.getAllUsers()).thenReturn(userList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(Constants.URL).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.NO_CONTENT.value(), status);
        verify(usersServiceImpl, times(1)).getAllUsers();
        TypeToken<List<Users>> token = new TypeToken<List<Users>>() {
        };
        List usersListResult = TestUtil.jsonToList(result.getResponse().getContentAsString(), token);
        logger.info("Get all users data list : {}", usersListResult);
        assertNull("Users not found", usersListResult);
    }


    @Test
    public void testUpdateUsers() throws Exception {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUsername( "Sherlock" );
        when(usersServiceImpl.getUsersById(any(Long.class))).thenReturn(userData);
        logger.info("Updated result of users : {}", userData);
        MvcResult result = mockMvc.
                perform(MockMvcRequestBuilders.put(Constants.URL + Constants.FORWARD_SLASH + "{id}", 11).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(TestUtil.objectToJson(userData))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.OK.value(), status);
        verify(usersServiceImpl, atLeastOnce()).update(any(Long.class), any(UserDTO.class));
    }

    @Test
    public void testUpdateUsersNotExists() throws Exception {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( (long) 11 );
        logger.info("Updated result of users : {}", userData);
        MvcResult result = mockMvc.
                perform(MockMvcRequestBuilders.put(Constants.URL + Constants.FORWARD_SLASH + "{id}", 11).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(TestUtil.objectToJson(userData))).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.NOT_FOUND.value(), status);
        verify(usersServiceImpl, only()).getUsersById(any(Long.class));
        verify(usersServiceImpl, never()).update(any(Long.class), any(UserDTO.class));
    }

    @Test
    public void testDeleteUsers() throws Exception {
        UserDTO userData = TestUtil.getTestUser();
        userData.setUserid( (long) 12);
        when(usersServiceImpl.getUsersById(any(Long.class))).thenReturn(userData);
        logger.info("Deleted users result : {}", userData);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(Constants.URL + Constants.FORWARD_SLASH + "{id}", 12)).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.OK.value(), status);
        verify(usersServiceImpl, atLeastOnce()).delete(any(Long.class));
    }

    @Test
    public void testDeleteUsersNotExists() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(Constants.URL + Constants.FORWARD_SLASH + "{id}", 12)).andReturn();
        Users resultUser = TestUtil.jsonToObject(result.getResponse().getContentAsString(), Users.class);
        int status = result.getResponse().getStatus();
        assertEquals( HttpStatus.NOT_FOUND.value(), status);
        assertNull(resultUser);
    }


}
