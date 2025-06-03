package com.tupinet.games.service;

import com.tupinet.games.model.Sala;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.repository.SalaRepository;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.DTO.SalaSolicitacaoDTO;
import com.tupinet.games.DTO.SalaRespostaDTO;
import com.tupinet.games.util.CodigoGerador;
import org.springframework.stereotype.Service;
import java.util.*;
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

        // Garantindo campo ativo nunca nulo
        if (dto.getAtivo() == null) {
            sala.setAtivo(true); // Valor padrão: ativa na criação
        } else {
            sala.setAtivo(dto.getAtivo());
        }

        sala.setCodigo(gerarCodigoUnico());

        Set<Jogo> jogos = new HashSet<>(jogoRepository.findAllById(dto.getJogosIds()));
        sala.setJogos(jogos);

        sala = salaRepository.save(sala);
        return toRespostaDTO(sala);
    }

    public SalaRespostaDTO editarSala(Integer id, SalaSolicitacaoDTO dto) {
        Sala sala = salaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        sala.setNome(dto.getNome());

        // Garantindo campo ativo nunca nulo na edição
        if (dto.getAtivo() == null) {
            sala.setAtivo(true); // Ou, se quiser, mantenha o valor antigo ou use false
        } else {
            sala.setAtivo(dto.getAtivo());
        }

        Set<Jogo> jogos = new HashSet<>(jogoRepository.findAllById(dto.getJogosIds()));
        sala.setJogos(jogos);

        sala = salaRepository.save(sala);
        return toRespostaDTO(sala);
    }

    public SalaRespostaDTO buscarSala(Integer id) {
        Sala sala = salaRepository.findById(id).orElseThrow(() -> new RuntimeException("Sala não encontrada"));
        return toRespostaDTO(sala);
    }

    public SalaRespostaDTO buscarSalaPorCodigo(String codigo) {
        Sala sala = salaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada pelo código"));
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
        dto.setJogosIds(
                sala.getJogos().stream().map(Jogo::getId).collect(Collectors.toSet())
        );
        return dto;
    }
}