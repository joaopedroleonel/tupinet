package com.tupinet.games.controller.api;

import com.tupinet.games.DTO.SalaApiDTO;
import com.tupinet.games.repository.SalaRepository;
import com.tupinet.games.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/salas")
public class SalaApiController {

    @Autowired SalaService salaService;

    private final SalaRepository salaRepository;

    @Autowired
    public SalaApiController(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @GetMapping
    public List<SalaApiDTO> listar() {return salaService.buscarSalasComJogos();}

    @GetMapping("/{id}")
    public SalaApiDTO buscarSalaPorId(@PathVariable Integer id){return salaService.buscarSalaComJogos(id);}

    @GetMapping("/por-codigo/{codigo}")
    public ResponseEntity<Integer> getSalaIdByCodigo(@PathVariable String codigo) {
        return salaRepository.findByCodigo(codigo)
                .map(sala -> ResponseEntity.ok(sala.getId()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}