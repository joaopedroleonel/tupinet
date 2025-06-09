package com.tupinet.games.service;

import com.tupinet.games.model.Sala;
import com.tupinet.games.model.SalaJogo;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.repository.SalaRepository;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.util.CodigoGerador;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final JogoRepository jogoRepository;

    public SalaService(SalaRepository salaRepository, JogoRepository jogoRepository) {
        this.salaRepository = salaRepository;
        this.jogoRepository = jogoRepository;
    }

    public SalaRespostaDTO criarSala(SalaSolicitacaoDTO dto) {
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

        Sala saved = salaRepository.save(sala);
        return toRespostaDTO(saved);
    }

    public SalaRespostaDTO editarSala(Integer id, SalaSolicitacaoDTO dto) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada: " + id));
        sala.setNome(dto.getNome());
        sala.setAtivo(dto.getAtivo() == null ? true : dto.getAtivo());

        sala.getSalaJogos().clear();
        for (Integer jogoId : dto.getJogosIds()) {
            Jogo jogo = jogoRepository.findById(jogoId)
                    .orElseThrow(() -> new RuntimeException("Jogo não encontrado: " + jogoId));
            SalaJogo sj = new SalaJogo();
            sj.setSala(sala);
            sj.setJogo(jogo);
            sala.getSalaJogos().add(sj);
        }

        Sala updated = salaRepository.save(sala);
        return toRespostaDTO(updated);
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

    public List<SalaRespostaDTO> listarSalas() {
        return salaRepository.findAll()
                .stream()
                .map(this::toRespostaDTO)
                .collect(Collectors.toList());
    }

    public void excluirSala(Integer id) {
        salaRepository.deleteById(id);
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
}
