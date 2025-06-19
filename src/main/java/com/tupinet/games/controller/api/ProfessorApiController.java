package com.tupinet.games.controller.api;

import com.tupinet.games.DTO.ListaProfessoresDTO;
import com.tupinet.games.DTO.ProfessorDTO;
import com.tupinet.games.model.Professor;
import com.tupinet.games.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professores")
public class ProfessorApiController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<ListaProfessoresDTO> listar() {
        return professorService.listarProfessores();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> cadastrar(@RequestBody ProfessorDTO dto) {
        try {
            Professor novo = professorService.salvarProfessor(dto.getNome(), dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok("Professor cadastrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
