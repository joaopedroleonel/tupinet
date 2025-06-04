package com.tupinet.games.controller;

import com.tupinet.games.service.SalaService;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.DTO.SalaRespostaDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SelecaoJogosController {
    private final SalaService salaService;
    private final JogoRepository jogoRepository;

    public SelecaoJogosController(SalaService salaService, JogoRepository jogoRepository) {
        this.salaService = salaService;
        this.jogoRepository = jogoRepository;
    }

    @GetMapping("/selecaoJogos")
    public String selecaoJogosAluno(@RequestParam("codigo") String codigo, Model model) {

        SalaRespostaDTO sala = salaService.buscarSalaPorCodigo(codigo);
        Set<Integer> jogosIds = sala.getJogosIds();
        List<Jogo> jogosDaSala = jogoRepository.findAllById(jogosIds);
        model.addAttribute("codigoSala", codigo);
        model.addAttribute("jogos", jogosDaSala);
        return "homeAlunoSelJogos";
    }
}