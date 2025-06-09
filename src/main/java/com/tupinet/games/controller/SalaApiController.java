package com.tupinet.games.controller;

import com.tupinet.games.model.Sala;
import com.tupinet.games.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/salas")
public class SalaApiController {

    private final SalaRepository salaRepository;

    @Autowired
    public SalaApiController(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @GetMapping("/por-codigo/{codigo}")
    public ResponseEntity<Integer> getSalaIdByCodigo(@PathVariable String codigo) {
        return salaRepository.findByCodigo(codigo)
                .map(sala -> ResponseEntity.ok(sala.getId()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}