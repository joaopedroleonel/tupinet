package com.tupinet.games.service;

import com.tupinet.games.DTO.ListaProfessoresDTO;
import com.tupinet.games.DTO.ProfessorDTO;
import com.tupinet.games.model.Professor;
import com.tupinet.games.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Professor salvarProfessor(String nome, String email, String senhaPura) {
        if (professorRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        Professor professor = new Professor();
        professor.setNome(nome);
        professor.setEmail(email);
        professor.setSenha(passwordEncoder.encode(senhaPura));

        return professorRepository.save(professor);
    }

    public List<ListaProfessoresDTO> listarProfessores() {
        List<Professor> professoresInicias = professorRepository.findAll();
        List<ListaProfessoresDTO> professores = new ArrayList<>();

        for (Professor prof: professoresInicias){
            ListaProfessoresDTO professor = new ListaProfessoresDTO();
            professor.setEmail(prof.getEmail());
            professor.setNome(prof.getNome());
            professores.add(professor);
        }

        return professores;
    }

    public Professor getProfessorLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return professorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado"));
    }

}