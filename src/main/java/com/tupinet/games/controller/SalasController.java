package com.tupinet.games.controller;

import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.service.SalaService;
import com.tupinet.games.repository.JogoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salas")
public class SalasController {
    private final SalaService salaService;
    private final JogoRepository jogoRepository;

    public SalasController(SalaService salaService, JogoRepository jogoRepository) {
        this.salaService = salaService;
        this.jogoRepository = jogoRepository;
    }

    @GetMapping
    public String listarSalas(Model model) {
        List<SalaRespostaDTO> salas = salaService.listarSalas();
        model.addAttribute("salas", salas);
        return "gerenciamentoSalas";
    }

    @GetMapping("/novo")
    public String exibirFormularioCriar(Model model) {
        model.addAttribute("sala", new SalaSolicitacaoDTO());
        model.addAttribute("jogos", jogoRepository.findAll());
        return "novaSala";
    }

    @PostMapping("/excluir/{id}")
    public String excluirSala(@PathVariable Integer id) {
        salaService.excluirSala(id);
        return "redirect:/salas";
    }

    @PostMapping
    public String criarSala(@ModelAttribute("sala") SalaSolicitacaoDTO dto) {
        salaService.criarSala(dto);
        return "redirect:/salas";
    }
}