package com.example.btl_dbclpm.controller;

import com.example.btl_dbclpm.model.User;
import com.example.btl_dbclpm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testLoginSuccess() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("quocviet");
        mockUser.setPassword("Viet@12345");

        when(userService.login("quocviet", "Viet@12345")).thenReturn(mockUser);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "quocviet")
                        .param("password", "Viet@12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("quocviet")));
    }


    @Test
    public void testLoginFailure() throws Exception {
        when(userService.login("quocviet", "viet123")).thenReturn(null);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "quocviet")
                        .param("password", "viet123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEmptyUsernameAndPassword001() throws Exception {
        mockMvc.perform(get("/api/user/login")
                        .param("username", "")
                        .param("password", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testEmptyUsername002() throws Exception {
        mockMvc.perform(get("/api/user/login")
                        .param("username", "")
                        .param("password", "Meapex123@"))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void testEmptyPassword003() throws Exception {
        mockMvc.perform(get("/api/user/login")
                        .param("username", "meapex123")
                        .param("password", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testInvalidUsername004() throws Exception {
        mockMvc.perform(get("/api/user/login")
                        .param("username", "/)*:+")
                        .param("password", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testInvalidPassword005() throws Exception {
        mockMvc.perform(get("/api/user/login")
                        .param("username", "meapex123")
                        .param("password", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testInvalidCredentials006() throws Exception {
        when(userService.login("meapex123", "meapex123")).thenReturn(null);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "meapex123")
                        .param("password", "meapex123"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void testValidCredentials007() throws Exception {
        User mockUser = new User();
        mockUser.setUsername("meapex123");
        mockUser.setPassword("meapex123");

        when(userService.login("meapex123", "meapex123")).thenReturn(mockUser);

        mockMvc.perform(get("/api/user/login")
                        .param("username", "meapex123")
                        .param("password", "meapex123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("meapex123")));
    }









}