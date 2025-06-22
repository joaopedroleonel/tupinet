package com.tupinet.games.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.service.PontuacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PontuacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
class PontuacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PontuacaoService pontuacaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveSalvarPontuacaoComSucesso() throws Exception {
        PontuacaoDTO dto = new PontuacaoDTO(1, "ABC123", "Joao", 100, 5);

        Mockito.when(pontuacaoService.setPontuacao(Mockito.any(PontuacaoDTO.class)))
                .thenReturn(new Pontuacao());

        mockMvc.perform(put("/salvar-pontuacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarBadRequestQuandoServicoFalha() throws Exception {
        PontuacaoDTO dto = new PontuacaoDTO(1, "ABC123", "Joao", 100, 5);

        Mockito.when(pontuacaoService.setPontuacao(Mockito.any(PontuacaoDTO.class)))
                .thenThrow(new RuntimeException("Erro simulado"));

        mockMvc.perform(put("/salvar-pontuacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
