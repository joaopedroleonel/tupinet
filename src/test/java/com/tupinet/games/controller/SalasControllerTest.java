package com.tupinet.games.controller;

import com.tupinet.games.DTO.RankingDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.service.PontuacaoService;
import com.tupinet.games.service.SalaService;
import com.tupinet.games.repository.JogoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalasController.class)
@AutoConfigureMockMvc(addFilters = false)
class SalasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalaService salaService;

    @MockBean
    private JogoRepository jogoRepository;

    @MockBean
    private PontuacaoService pontuacaoService;

    @Test
    void deveListarSalas() throws Exception {
        SalaRespostaDTO sala = new SalaRespostaDTO();
        sala.setId(1);
        sala.setNome("Sala Teste");
        sala.setCodigo("ABC123");
        sala.setAtivo(true);
        sala.setRota("portugues");
        sala.setJogosIds(Set.of(1, 2));

        when(salaService.listarSalas()).thenReturn(List.of(sala));

        mockMvc.perform(get("/salas"))
                .andExpect(status().isOk())
                .andExpect(view().name("gerenciamentoSalas"))
                .andExpect(model().attributeExists("salas"));
    }


    @Test
    void deveExibirFormularioCriarSala() throws Exception {
        when(jogoRepository.findAll()).thenReturn(Collections.singletonList(new Jogo()));

        mockMvc.perform(get("/salas/novo"))
                .andExpect(status().isOk())
                .andExpect(view().name("novaSala"))
                .andExpect(model().attributeExists("sala"))
                .andExpect(model().attributeExists("jogos"));
    }

    @Test
    void deveExcluirSala() throws Exception {
        Mockito.doNothing().when(salaService).excluirSala(1);

        mockMvc.perform(post("/salas/excluir/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/salas"));
    }

    @Test
    void deveCriarSala() throws Exception {
        when(salaService.criarSala(Mockito.any(SalaSolicitacaoDTO.class))).thenReturn(null);


        mockMvc.perform(post("/salas")
                        .param("nome", "Nova Sala Teste")
                        .param("ativo", "true")
                        .param("jogosIds", "1", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/salas"));
    }

    @Test
    void deveExibirRanking() throws Exception {
        List<RankingDTO> ranking = new ArrayList<>(List.of(
                new RankingDTO("Aluno", 5, 100, "Jogo")
        ));

        when(pontuacaoService.findPontuacaoBySalaId(Mockito.anyInt()))
                .thenReturn(ranking);

        mockMvc.perform(get("/salas/ver/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("verSala"))
                .andExpect(model().attributeExists("rankingList"));
    }
}