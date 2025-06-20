package com.tupinet.games.service;

import com.tupinet.games.DTO.ListaProfessoresDTO;
import com.tupinet.games.model.Professor;
import com.tupinet.games.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {
    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfessorService professorService;

    @Test
    void deveSalvarProfessorComSenhaCodificada() {
        String nome = "Izarut";
        String email = "izarut@tupinet.com";
        String senhaPura = "senha123";
        String senhaCodificada = "hashedSenha123";

        when(professorRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(senhaPura)).thenReturn(senhaCodificada);

        Professor professorSalvo = new Professor();
        professorSalvo.setNome(nome);
        professorSalvo.setEmail(email);
        professorSalvo.setSenha(senhaCodificada);

        when(professorRepository.save(any(Professor.class))).thenReturn(professorSalvo);

        Professor resultado = professorService.salvarProfessor(nome, email, senhaPura);

        assertNotNull(resultado);
        assertEquals(nome, resultado.getNome());
        assertEquals(email, resultado.getEmail());
        assertEquals(senhaCodificada, resultado.getSenha());
    }

    @Test
    void deveListarTodosOsProfessores() {
        Professor prof1 = new Professor();
        prof1.setNome("Ana");
        prof1.setEmail("ana@tupi.com");

        Professor prof2 = new Professor();
        prof2.setNome("Beto");
        prof2.setEmail("beto@tupi.com");

        when(professorRepository.findAll()).thenReturn(Arrays.asList(prof1, prof2));

        List<ListaProfessoresDTO> resultado = professorService.listarProfessores();

        assertEquals(2, resultado.size());
        assertEquals("Ana", resultado.get(0).getNome());
        assertEquals("Beto", resultado.get(1).getNome());
    }

    @Test
    void deveRetornarProfessorLogado() {
        String email = "professor@tupi.com";
        Professor professor = new Professor();
        professor.setEmail(email);
        professor.setNome("Izarut");

        UserDetails userDetails = new User(email, "senha", new ArrayList<>());
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn(email);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(professorRepository.findByEmail(email)).thenReturn(Optional.of(professor));

        Professor resultado = professorService.getProfessorLogado();

        assertNotNull(resultado);
        assertEquals(email, resultado.getEmail());
        assertEquals("Izarut", resultado.getNome());
    }

}