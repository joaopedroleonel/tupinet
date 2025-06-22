package com.tupinet.games.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(HomeAlunoController.class)
class HomeAlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarPaginaDoCodigoAluno() throws Exception {
        mockMvc.perform(get("/codAluno"))
                .andExpect(status().isOk())
                .andExpect(view().name("codigoAluno.html"));
    }
}
