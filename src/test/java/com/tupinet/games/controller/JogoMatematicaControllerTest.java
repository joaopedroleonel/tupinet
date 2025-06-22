package com.tupinet.games.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JogoMatematicaController.class)
@AutoConfigureMockMvc(addFilters = false)
class JogoMatematicaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarPaginaJogoMatematica() throws Exception {
        mockMvc.perform(get("/jogo-matematica"))
                .andExpect(status().isOk())
                .andExpect(view().name("matematica.html"));
    }
}
