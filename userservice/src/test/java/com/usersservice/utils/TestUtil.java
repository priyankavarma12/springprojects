package com.usersservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usersservice.dto.UserDTO;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestUtil {

    public static UserDTO getTestUser() {
        return UserDTO.builder()
                .username("John").address("ScotLand,UK").phoneno( "+10 1234567890" )
                .build();
    }

    public static void compareTwoUsersExcludingId(UserDTO userFromApp, UserDTO testUser) {
        assertThat(userFromApp.getUsername()).isEqualTo(testUser.getUsername());
        assertThat(userFromApp.getAddress()).isEqualTo(testUser.getAddress());
        assertThat(userFromApp.getPhoneno()).isEqualTo(testUser.getPhoneno());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<UserDTO> listOfUsers() {
        UserDTO u1 = new UserDTO((long)1,
                "John", "ScotLand,James","+1 1234567890");
        UserDTO u2 = new UserDTO((long)2,
                "Henry", "India, Hyderabad","+91 9090901234");
        return Arrays.asList(u1, u2);
    }

    public static List jsonToList(String json, TypeToken token) {
        Gson gson = new Gson();
        return gson.fromJson(json, token.getType());
    }

    public static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static <T> T jsonToObject(String json, Class<T> classOf) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOf);
    }
}
