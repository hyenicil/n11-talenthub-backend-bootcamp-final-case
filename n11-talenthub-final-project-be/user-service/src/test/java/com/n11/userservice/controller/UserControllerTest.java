package com.n11.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n11.userservice.dto.user.UserResponse;
import com.n11.userservice.dto.user.UserSaveRequest;
import com.n11.userservice.dto.user.UserUpdateRequest;
import com.n11.userservice.entity.enums.Gender;
import com.n11.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService mockUserService;

    @Test
    void shouldSaveValidUserRequest() throws Exception {

        // given
        UserSaveRequest request = new UserSaveRequest("name", "surname", LocalDate.of(2020, 1, 1), "email@mail.com", Gender.MALE);

        String requestAsString = objectMapper.writeValueAsString(request);

        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .content(requestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        // then
        boolean success = mvcResult.getResponse().getStatus() == 201;

        assertTrue(success);
    }

    @Test
    void shouldGetUserById() throws Exception {

        // given
        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email@mail.com",
                Gender.MALE);

        // when
        when(mockUserService.getById(0L)).thenReturn(userResponse);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/users/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldGetAllUsers() throws Exception {

        // given
        List<UserResponse> userResponses = List.of(
                new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email@mail.com", Gender.MALE));

        // when
        when(mockUserService.getAll()).thenReturn(userResponses);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldNotGetAnyUserWhenUserServiceReturnsNoItem() throws Exception {

        // when
        when(mockUserService.getAll()).thenReturn(Collections.emptyList());

        MockHttpServletResponse response = mockMvc.perform(get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldUpdateUser() throws Exception {

        // given
        UserUpdateRequest request = new UserUpdateRequest("name", "surname", LocalDate.of(2020, 1, 1), "email@mail.com", Gender.MALE);

        String requestAsString = objectMapper.writeValueAsString(request);

        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/users/1", 0)
                        .content(requestAsString).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        boolean success = response.getStatus() == 200;

        assertTrue(success);
    }

    @Test
    void shouldDeactivateUser() throws Exception {

        // given
        UserResponse userResponse = new UserResponse(0L, "name", "surname", LocalDate.of(2020, 1, 1), "email@mail.com",
                Gender.MALE);

        // when
        when(mockUserService.deactivate(0L)).thenReturn(userResponse);

        MockHttpServletResponse response = mockMvc.perform(patch("/api/v1/users/deactivate/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldDeleteUser() throws Exception {

        // when
        MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/users/{id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
