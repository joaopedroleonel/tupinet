package com.tupinet.games.controller;

import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.service.SalaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SelecaoJogosController.class)
@AutoConfigureMockMvc(addFilters = false)
class SelecaoJogosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @MockBean
    private JogoRepository jogoRepository;

    @Test
    void deveRetornarHomeAlunoSelJogos() throws Exception {
        String testCodigo = "SALAXYZ";
        Set<Integer> jogosIds = new HashSet<>(Arrays.asList(1, 2));

        SalaRespostaDTO mockSalaRespostaDTO = new SalaRespostaDTO();
        mockSalaRespostaDTO.setCodigo(testCodigo);
        mockSalaRespostaDTO.setJogosIds(jogosIds);

        Jogo mockJogo1 = new Jogo();
        mockJogo1.setId(1);
        mockJogo1.setNome("Jogo Teste 1");

        Jogo mockJogo2 = new Jogo();
        mockJogo2.setId(2);
        mockJogo2.setNome("Jogo Teste 2");

        List<Jogo> mockJogosDaSala = Arrays.asList(mockJogo1, mockJogo2);

        when(salaService.buscarSalaPorCodigo(testCodigo)).thenReturn(mockSalaRespostaDTO);
        when(jogoRepository.findAllById(jogosIds)).thenReturn(mockJogosDaSala);

        mockMvc.perform(get("/selecaoJogos")
                        .param("codigo", testCodigo))
                .andExpect(status().isOk())
                .andExpect(view().name("homeAlunoSelJogos"))
                .andExpect(model().attribute("codigoSala", testCodigo))
                .andExpect(model().attribute("jogos", mockJogosDaSala));

        verify(salaService, times(1)).buscarSalaPorCodigo(testCodigo);
        verify(jogoRepository, times(1)).findAllById(jogosIds);
    }

    @Test
    void deveRetornarSelJogosListaVazia() throws Exception {
        String testCodigo = "SALASEMJOGOS";
        Set<Integer> jogosIds = new HashSet<>();

        SalaRespostaDTO mockSalaRespostaDTO = new SalaRespostaDTO();
        mockSalaRespostaDTO.setCodigo(testCodigo);
        mockSalaRespostaDTO.setJogosIds(jogosIds);

        when(salaService.buscarSalaPorCodigo(testCodigo)).thenReturn(mockSalaRespostaDTO);
        when(jogoRepository.findAllById(jogosIds)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/selecaoJogos")
                        .param("codigo", testCodigo))
                .andExpect(status().isOk())
                .andExpect(view().name("homeAlunoSelJogos"))
                .andExpect(model().attribute("codigoSala", testCodigo))
                .andExpect(model().attribute("jogos", Arrays.asList()));

        verify(salaService, times(1)).buscarSalaPorCodigo(testCodigo);
        verify(jogoRepository, times(1)).findAllById(jogosIds);
    }

    @Test
    void deveRedirecionarParaErrorPage1() throws Exception {
        String invalidCodigo = "CODIGOINEXISTENTE";

        when(salaService.buscarSalaPorCodigo(invalidCodigo))
                .thenThrow(new RuntimeException("Sala não encontrada pelo código: " + invalidCodigo));

        mockMvc.perform(get("/selecaoJogos")
                        .param("codigo", invalidCodigo))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/codAluno?error=true"));

        verify(salaService, times(1)).buscarSalaPorCodigo(invalidCodigo);
        verify(jogoRepository, never()).findAllById(anySet());
    }

    @Test
    void deveRedirecionarParaErrorPage2xd() throws Exception {
        String testCodigo = "CODIGOEXCECAO";

        when(salaService.buscarSalaPorCodigo(testCodigo))
                .thenThrow(new IllegalStateException("Erro inesperado no serviço de sala"));

        mockMvc.perform(get("/selecaoJogos")
                        .param("codigo", testCodigo))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/codAluno?error=true"));

        verify(salaService, times(1)).buscarSalaPorCodigo(testCodigo);
        verify(jogoRepository, never()).findAllById(anySet());
    }

    @Test
    void deveRetornarBadRequest() throws Exception {
        mockMvc.perform(get("/selecaoJogos"))
                .andExpect(status().isBadRequest());

        verify(salaService, never()).buscarSalaPorCodigo(anyString());
        verify(jogoRepository, never()).findAllById(anySet());
    }
}