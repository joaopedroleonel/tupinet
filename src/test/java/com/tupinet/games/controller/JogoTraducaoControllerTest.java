package com.tupinet.games.controller;

import com.tupinet.games.service.TraducaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JogoTraducaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class JogoTraducaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TraducaoService traducaoService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void deveRetornarPaginaJogoTraducaoComModelo() throws Exception {
        Map<String, String> palavraTraducao = new HashMap<>();
        palavraTraducao.put("palavra", "banana");
        palavraTraducao.put("traducao", "banana");

        when(traducaoService.getPalavraAleatoriaComTraducao())
                .thenReturn(Optional.of(palavraTraducao));

        mockMvc.perform(get("/jogo-traducao"))
                .andExpect(status().isOk())
                .andExpect(view().name("traducao.html"))
                .andExpect(model().attribute("palavra", "banana"))
                .andExpect(model().attribute("traducao", "banana"));
    }

    @Test
    void deveRetornarJsonPalavraAleatoria() throws Exception {
        Map<String, String> palavraTraducao = new HashMap<>();
        palavraTraducao.put("palavra", "banana");
        palavraTraducao.put("traducao", "banana");

        when(traducaoService.getPalavraAleatoriaComTraducao())
                .thenReturn(Optional.of(palavraTraducao));

        mockMvc.perform(get("/traducao-aleatoria"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.palavra").value("banana"))
                .andExpect(jsonPath("$.traducao").value("banana"));
    }
}