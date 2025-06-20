package com.tupinet.games.service;

import com.tupinet.games.model.Palavra;
import com.tupinet.games.model.Palavreca;
import com.tupinet.games.repository.JogoPalavraRepository;
import com.tupinet.games.repository.PalavraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PalavrecaServiceTest {

    @Mock
    private PalavraRepository palavraRepository;
    @Mock
    private JogoPalavraRepository jogoPalavraRepository;

    @InjectMocks
    private PalavrecaService palavrecaService;

    @Test
    void deveRetornarPalavraAleatoria() {
        Palavra palavra = new Palavra();
        palavra.setTexto("Dog");

        Palavra palavra2 = new Palavra();
        palavra2.setTexto("House");

        when(jogoPalavraRepository.findPalavrasByJogoId(2)).thenReturn(Arrays.asList(palavra, palavra2));

        assertNotNull(palavrecaService.getPalavraAleatoria());
    }

    @Test
    void deveRetornarFalseParaPalavraErrada() {
        Palavreca palavra = null;
        Map<Integer, Character> respostas = null;

        assertEquals(false, palavrecaService.validarResposta(palavra,respostas));
    }
}