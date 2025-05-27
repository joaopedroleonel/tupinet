package com.tupinet.games.service;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.model.Jogo;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.model.Sala;
import com.tupinet.games.repository.JogoRepository;
import com.tupinet.games.repository.PontuacaoRepository;
import com.tupinet.games.repository.SalaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PontuacaoService {

    @Autowired
    private PontuacaoRepository pontuacaoRepository;

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public Pontuacao setPontuacao(PontuacaoDTO dto){

        Pontuacao pontuacao = new Pontuacao();

        Integer jogoId = dto.getJogoId();
        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new IllegalArgumentException("Jogo não encontrado: " + jogoId));

        Integer salaId = dto.getSalaId();
        Sala sala = salaRepository.findById(salaId)
                .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada: " + salaId));

        pontuacao.setJogo(jogo);
        pontuacao.setSala(sala);
        pontuacao.setData(new Date());
        pontuacao.setAcertos(dto.getAcertos());
        pontuacao.setAluno(dto.getAluno());
        pontuacao.setPontos(dto.getPontos());

        return pontuacaoRepository.save(pontuacao);

    }

}
