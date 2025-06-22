package com.tupinet.games.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginProfessorController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginProfessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarPaginaLoginProfessor() throws Exception {
        mockMvc.perform(get("/login-professor"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginProfessor.html"));
    }
}