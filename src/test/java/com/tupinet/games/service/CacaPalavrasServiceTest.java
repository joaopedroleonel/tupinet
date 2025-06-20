package com.tupinet.games.service;

import com.tupinet.games.DTO.PosicaoPalavraDTO;
import com.tupinet.games.repository.JogoPalavraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CacaPalavrasServiceTest {

    @Mock
    JogoPalavraRepository jogoPalavraRepository;

    @InjectMocks
    private CacaPalavrasService cacaPalavrasService;

    @Test
    void deveRetornarPalavraAleatoria() {
        PosicaoPalavraDTO palavra1 = new PosicaoPalavraDTO();
        palavra1.setPalavra("Bacana");

        PosicaoPalavraDTO palavra2 = new PosicaoPalavraDTO();
        palavra2.setPalavra("Banana");

        List<PosicaoPalavraDTO> listaMockada = Arrays.asList(palavra1, palavra2);
        when(jogoPalavraRepository.findPalavrasEPosicaoByJogoId(3)).thenReturn(listaMockada);

        PosicaoPalavraDTO resultado = cacaPalavrasService.getPalavraAleatoria();

        assertNotNull(resultado);
        assertTrue(resultado.getPalavra().equals("Bacana") || resultado.getPalavra().equals("Banana"));
    }
}
