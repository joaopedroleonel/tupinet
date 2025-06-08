package com.tupinet.games.service;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.model.SalaJogo;
import com.tupinet.games.repository.PontuacaoRepository;
import com.tupinet.games.repository.SalaJogoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PontuacaoService {

    @Autowired
    private PontuacaoRepository pontuacaoRepository;

    @Autowired
    private SalaJogoRepository salaJogoRepository;

    @Transactional
    public Pontuacao setPontuacao(PontuacaoDTO dto) {

        SalaJogo salaJogo = salaJogoRepository
                .findBySalaIdAndJogoId(dto.getSalaId(), dto.getJogoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Associação Sala-Jogo não encontrada: sala=" + dto.getSalaId() + ", jogo=" + dto.getJogoId()));

        Pontuacao pontuacao = new Pontuacao();
        pontuacao.setSalaJogo(salaJogo);
        pontuacao.setData(new Date());
        pontuacao.setAcertos(dto.getAcertos());
        pontuacao.setAluno(dto.getAluno());
        pontuacao.setPontos(dto.getPontos());

        return pontuacaoRepository.save(pontuacao);
    }
}
