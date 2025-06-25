package com.tupinet.games.service;

import com.tupinet.games.DTO.SalaApiDTO;
import com.tupinet.games.model.Professor;
import com.tupinet.games.model.Sala;
import com.tupinet.games.model.SalaJogo;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.repository.SalaRepository;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.util.CodigoGerador;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final JogoRepository jogoRepository;
    private final ProfessorService professorService;

    public SalaService(SalaRepository salaRepository, JogoRepository jogoRepository, ProfessorService professorService) {
        this.salaRepository = salaRepository;
        this.jogoRepository = jogoRepository;
        this.professorService = professorService;
    }

    @Transactional
    public SalaRespostaDTO criarSala(SalaSolicitacaoDTO dto) {
        Professor professor = professorService.getProfessorLogado();

        Sala sala = new Sala();
        sala.setNome(dto.getNome());
        sala.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());
        sala.setCodigo(gerarCodigoUnico());

        Set<SalaJogo> salaJogos = new HashSet<>();
        for (Integer jogoId : dto.getJogosIds()) {
            Jogo jogo = jogoRepository.findById(jogoId)
                    .orElseThrow(() -> new RuntimeException("Jogo não encontrado: " + jogoId));
            SalaJogo sj = new SalaJogo();
            sj.setSala(sala);
            sj.setJogo(jogo);
            salaJogos.add(sj);
        }
        sala.setSalaJogos(salaJogos);

        sala.getProfessores().add(professor);
        professor.getSalas().add(sala);

        Sala saved = salaRepository.save(sala);
        return toRespostaDTO(saved);
    }

    public SalaRespostaDTO buscarSala(Integer id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada: " + id));
        return toRespostaDTO(sala);
    }

    public SalaRespostaDTO buscarSalaPorCodigo(String codigo) {
        Sala sala = salaRepository.findWithSalaJogosByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada pelo código: " + codigo));
        return toRespostaDTO(sala);
    }

    @Transactional
    public List<SalaRespostaDTO> listarSalas() {
        Professor professor = professorService.getProfessorLogado();
        return professor.getSalas()
                .stream()
                .filter(Sala::getAtivo)
                .map(this::toRespostaDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void excluirSala(Integer id) {
        Professor professor = professorService.getProfessorLogado();

        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        if (!sala.getProfessores().contains(professor)) {
            throw new RuntimeException("Você não tem permissão para excluir esta sala.");
        }

        for (Professor p : sala.getProfessores()) {
            p.getSalas().remove(sala);
        }

        sala.getProfessores().clear();

        salaRepository.delete(sala);
    }

    private String gerarCodigoUnico() {
        String codigo;
        do {
            codigo = CodigoGerador.gerarCodigo(5);
        } while (salaRepository.existsByCodigo(codigo));
        return codigo;
    }

    private SalaRespostaDTO toRespostaDTO(Sala sala) {
        SalaRespostaDTO dto = new SalaRespostaDTO();
        dto.setId(sala.getId());
        dto.setNome(sala.getNome());
        dto.setCodigo(sala.getCodigo());
        dto.setAtivo(sala.getAtivo());
        Set<Integer> jogosIds = sala.getSalaJogos().stream()
                .map(sj -> sj.getJogo().getId())
                .collect(Collectors.toSet());
        dto.setJogosIds(jogosIds);
        return dto;
    }

    public List<SalaApiDTO> buscarSalasComJogos() {
        List<Sala> salas = salaRepository.findAllComJogos();

        return salas.stream().map(sala -> {
            List<Jogo> jogos = sala.getSalaJogos()
                    .stream()
                    .map(SalaJogo::getJogo)
                    .distinct()
                    .toList();

            return new SalaApiDTO(
                    sala.getId(),
                    sala.getNome(),
                    sala.getCodigo(),
                    sala.getAtivo(),
                    jogos
            );
        }).toList();
    }

    public SalaApiDTO buscarSalaComJogos(Integer id) {
        Sala sala = salaRepository.findByIdComJogos(id)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        List<Jogo> jogos = sala.getSalaJogos().stream()
                .map(SalaJogo::getJogo)
                .distinct()
                .toList();

        return new SalaApiDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCodigo(),
                sala.getAtivo(),
                jogos
        );
    }
}
