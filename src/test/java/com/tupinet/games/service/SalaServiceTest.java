package com.tupinet.games.service;

import com.tupinet.games.DTO.SalaApiDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.model.Professor;
import com.tupinet.games.model.Sala;
import com.tupinet.games.model.SalaJogo;
import com.tupinet.games.repository.JogoPalavraRepository;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.repository.SalaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {
    @Mock
    private SalaRepository salaRepository;

    @Mock
    private JogoRepository jogoRepository;

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private SalaService salaService;

    @Test
    void deveCriarSalaComJogosEProfessor() {
        Professor professor = new Professor();
        professor.setId(1);
        professor.setSalas(new HashSet<>());

        Jogo jogo1 = new Jogo();
        jogo1.setId(10);

        SalaSolicitacaoDTO dto = new SalaSolicitacaoDTO();
        dto.setNome("Sala de Teste");
        dto.setAtivo(true);
        dto.setJogosIds(Set.of(10));

        when(professorService.getProfessorLogado()).thenReturn(professor);
        when(jogoRepository.findById(10)).thenReturn(Optional.of(jogo1));
        when(salaRepository.existsByCodigo(anyString())).thenReturn(false);
        when(salaRepository.save(any(Sala.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SalaRespostaDTO resposta = salaService.criarSala(dto);

        assertNotNull(resposta);
        assertEquals("Sala de Teste", resposta.getNome());
        assertTrue(resposta.getJogosIds().contains(10));
    }

    @Test
    void deveBuscarSalaPorId() {
        Sala sala = new Sala();
        sala.setId(5);
        sala.setNome("Sala ABC");
        sala.setCodigo("XYZ12");
        sala.setAtivo(true);
        sala.setSalaJogos(new HashSet<>());

        when(salaRepository.findById(5)).thenReturn(Optional.of(sala));

        SalaRespostaDTO resposta = salaService.buscarSala(5);

        assertNotNull(resposta);
        assertEquals(5, resposta.getId());
        assertEquals("Sala ABC", resposta.getNome());
        assertEquals("XYZ12", resposta.getCodigo());
    }

    @Test
    void deveBuscarSalaPorCodigo() {
        Sala sala = new Sala();
        sala.setId(3);
        sala.setNome("Sala Código");
        sala.setCodigo("ABC12");
        sala.setAtivo(true);
        sala.setSalaJogos(new HashSet<>());

        when(salaRepository.findWithSalaJogosByCodigo("ABC12")).thenReturn(Optional.of(sala));

        SalaRespostaDTO resposta = salaService.buscarSalaPorCodigo("ABC12");

        assertNotNull(resposta);
        assertEquals(3, resposta.getId());
        assertEquals("Sala Código", resposta.getNome());
        assertEquals("ABC12", resposta.getCodigo());
    }

    @Test
    void deveListarSalasAtivasDoProfessorLogado() {
        Sala sala1 = new Sala();
        sala1.setId(1);
        sala1.setNome("Sala A");
        sala1.setCodigo("AAA01");
        sala1.setAtivo(true);
        sala1.setSalaJogos(new HashSet<>());

        Sala sala2 = new Sala();
        sala2.setId(2);
        sala2.setNome("Sala B");
        sala2.setCodigo("BBB02");
        sala2.setAtivo(true);
        sala2.setSalaJogos(new HashSet<>());

        Sala salaInativa = new Sala();
        salaInativa.setId(3);
        salaInativa.setNome("Sala C");
        salaInativa.setAtivo(false);

        Professor professor = new Professor();
        professor.setId(99);
        professor.setSalas(Set.of(sala1, sala2, salaInativa));

        when(professorService.getProfessorLogado()).thenReturn(professor);

        List<SalaRespostaDTO> resultado = salaService.listarSalas();

        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(s -> s.getNome().equals("Sala A")));
        assertTrue(resultado.stream().anyMatch(s -> s.getNome().equals("Sala B")));
    }


    @Test
    void deveExcluirSalaSeProfessorForDono() {
        Integer salaId = 1;

        Professor professor = new Professor();
        professor.setId(100);

        Sala sala = new Sala();
        sala.setId(salaId);
        sala.setProfessores(Set.of(professor));

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
        when(professorService.getProfessorLogado()).thenReturn(professor);

        assertDoesNotThrow(() -> salaService.excluirSala(salaId));

        verify(salaRepository).delete(sala);
    }

    @Test
    void deveLancarExcecaoAoExcluirSalaSeProfessorNaoForDono() {
        Integer salaId = 2;

        Professor donoSala = new Professor();
        donoSala.setId(1);

        Professor professorLogado = new Professor();
        professorLogado.setId(2);

        Sala sala = new Sala();
        sala.setId(salaId);
        sala.setProfessores(Set.of(donoSala));

        when(salaRepository.findById(salaId)).thenReturn(Optional.of(sala));
        when(professorService.getProfessorLogado()).thenReturn(professorLogado);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            salaService.excluirSala(salaId);
        });

        assertEquals("Você não tem permissão para excluir esta sala.", exception.getMessage());
        verify(salaRepository, never()).delete(any());
    }

    @Test
    void deveBuscarTodasAsSalasComJogos() {
        Jogo jogo = new Jogo();
        jogo.setId(1);
        jogo.setNome("Caça-palavras");

        SalaJogo salaJogo = new SalaJogo();
        salaJogo.setJogo(jogo);

        Sala sala = new Sala();
        sala.setId(1);
        sala.setNome("Sala 1");
        sala.setCodigo("X1Y2Z");
        sala.setAtivo(true);
        sala.setSalaJogos(Set.of(salaJogo));

        when(salaRepository.findAllComJogos()).thenReturn(List.of(sala));

        List<SalaApiDTO> resultado = salaService.buscarSalasComJogos();

        assertEquals(1, resultado.size());
        assertEquals("Sala 1", resultado.get(0).getNome());
        assertEquals(1, resultado.get(0).getJogos().size());
        assertEquals("Caça-palavras", resultado.get(0).getJogos().get(0).getNome());
    }

    @Test
    void deveBuscarSalaEspecificaComJogos() {
        Jogo jogo = new Jogo();
        jogo.setId(7);
        jogo.setNome("Tradutor");

        SalaJogo salaJogo = new SalaJogo();
        salaJogo.setJogo(jogo);

        Sala sala = new Sala();
        sala.setId(5);
        sala.setNome("Sala Tradutor");
        sala.setCodigo("TRAD7");
        sala.setAtivo(true);
        sala.setSalaJogos(Set.of(salaJogo));

        when(salaRepository.findByIdComJogos(5)).thenReturn(Optional.of(sala));

        SalaApiDTO resultado = salaService.buscarSalaComJogos(5);

        assertNotNull(resultado);
        assertEquals("Sala Tradutor", resultado.getNome());
        assertEquals(1, resultado.getJogos().size());
        assertEquals("Tradutor", resultado.getJogos().get(0).getNome());
    }

}