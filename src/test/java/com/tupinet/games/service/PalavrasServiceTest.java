package com.tupinet.games.service;

import com.tupinet.games.DTO.PalavraComTraducaoDTO;
import com.tupinet.games.repository.PalavraRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PalavrasServiceTest {

    @Mock
    private PalavraRepository palavraRepository;

    @InjectMocks
    private PalavrasService palavrasService;

    @Test
    void deveRetornarListaDePalavrasComTraducoes() {

        PalavraComTraducaoDTO dto1 = new PalavraComTraducaoDTO(1, "Dog", "Tupi");
        PalavraComTraducaoDTO dto2 = new PalavraComTraducaoDTO(2, "House", "Tupi");

        when(palavraRepository.findPalavrasComTraducoes()).thenReturn(Arrays.asList(dto1, dto2));


        List<PalavraComTraducaoDTO> resultado = palavrasService.listarPalavrasComTraducoes();


        assertEquals(2, resultado.size());
        assertEquals("Dog", resultado.get(0).getTexto());
        assertEquals("Tupi", resultado.get(0).getTraducao());
        assertEquals("House", resultado.get(1).getTexto());
        assertEquals("Tupi", resultado.get(0).getTraducao());

        verify(palavraRepository, times(1)).findPalavrasComTraducoes();
    }
}
