package com.tupinet.games.service;

import com.tupinet.games.DTO.RankingDTO;
import com.tupinet.games.model.Professor;
import com.tupinet.games.model.Sala;
import com.tupinet.games.repository.PontuacaoRepository;
import com.tupinet.games.repository.SalaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PontuacaoServiceTest {

    @Mock
    private PontuacaoRepository pontuacaoRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private PontuacaoService pontuacaoService;

    @Test
    void deveRetornarRankingDaSalaSeProfessorPertencer() {
        Integer salaId = 1;

        Professor professor = new Professor();
        professor.setId(10);

        Sala sala = new Sala();
        sala.setId(salaId);

        Set<Professor> professores = new HashSet<>();
        professores.add(professor);
        sala.setProfessores(professores);

        RankingDTO ranking = new RankingDTO("izas", 10, 100, "banana");
        List<RankingDTO> rankingMockado = Arrays.asList(ranking);

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
        when(professorService.getProfessorLogado()).thenReturn(professor);
        when(pontuacaoRepository.findPontuacaoBySalaId(salaId)).thenReturn(rankingMockado);

        List<RankingDTO> resultado = pontuacaoService.findPontuacaoBySalaId(salaId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("izas", resultado.get(0).getAluno());
    }


    @Test
    void deveLancarExcecaoSeProfessorNaoPertencerASala() {
        Integer salaId = 1;

        Professor professorDaSala = new Professor();
        professorDaSala.setId(1);

        Professor professorLogado = new Professor();
        professorLogado.setId(2);

        Sala sala = new Sala();
        sala.setId(salaId);
        Professor professor = new Professor();
        professor.setId(10);
        Set<Professor> professores = new HashSet<>();
        professores.add(professor);

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
        when(professorService.getProfessorLogado()).thenReturn(professorLogado);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pontuacaoService.findPontuacaoBySalaId(salaId);
        });

        assertEquals("Acesso negado à sala.", exception.getMessage());
    }
}