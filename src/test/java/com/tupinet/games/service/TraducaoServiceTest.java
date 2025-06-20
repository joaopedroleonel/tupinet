package com.tupinet.games.service;

import com.tupinet.games.model.Palavra;
import com.tupinet.games.model.TraducaoPalavra;
import com.tupinet.games.repository.JogoPalavraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraducaoServiceTest {
    @Mock
    private JogoPalavraRepository jogoPalavraRepository;

    @InjectMocks
    private TraducaoService traducaoService;

    @Test
    void deveRetornarPalavraComTraducao() {
        Palavra palavra = new Palavra();
        palavra.setTexto("Cachorro");

        TraducaoPalavra traducao = new TraducaoPalavra();
        traducao.setTraducao("Dog");

        palavra.setTraducoes(Set.of(traducao));

        when(jogoPalavraRepository.findPalavrasByJogoId(1)).thenReturn(List.of(palavra));

        Optional<Map<String, String>> resultado = traducaoService.getPalavraAleatoriaComTraducao();

        assertTrue(resultado.isPresent());
        assertEquals("Cachorro", resultado.get().get("palavra"));
        assertEquals("Dog", resultado.get().get("traducao"));
    }

    @Test
    void deveRetornarOptionalVazioSeNaoHouverPalavras() {
        when(jogoPalavraRepository.findPalavrasByJogoId(1)).thenReturn(Collections.emptyList());

        Optional<Map<String, String>> resultado = traducaoService.getPalavraAleatoriaComTraducao();

        assertTrue(resultado.isEmpty());
    } 
}