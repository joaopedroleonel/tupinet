package com.tupinet.games.controller;

import com.tupinet.games.DTO.PosicaoPalavraDTO;
import com.tupinet.games.service.CacaPalavrasService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(jogoCacaPalavrasController.class)
class JogoCacaPalavrasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CacaPalavrasService cacaPalavrasService;

    @Test
    void deveRetornarHtmlCacaPalavras() throws Exception {
        mockMvc.perform(get("/jogocacapalavras"))
                .andExpect(status().isOk())
                .andExpect(view().name("jogoCacaPalavras.html"));
    }

    @Test
    void deveRetornarPalavraAleatoriaComoJson() throws Exception {
        PosicaoPalavraDTO dto = new PosicaoPalavraDTO("A1", "banana");

        Mockito.when(cacaPalavrasService.getPalavraAleatoria()).thenReturn(dto);

        mockMvc.perform(get("/apicacapalavras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.palavra").value("banana"))
                .andExpect(jsonPath("$.posicao").value("A1"));
    }
}