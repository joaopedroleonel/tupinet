package com.tupinet.games.service;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.DTO.RankingDTO;
import com.tupinet.games.model.Pontuacao;
import com.tupinet.games.model.Professor;
import com.tupinet.games.model.Sala;
import com.tupinet.games.model.SalaJogo;
import com.tupinet.games.repository.PontuacaoRepository;
import com.tupinet.games.repository.SalaJogoRepository;
import com.tupinet.games.repository.SalaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PontuacaoService {

    @Autowired
    private PontuacaoRepository pontuacaoRepository;

    @Autowired
    private ProfessorService professorService;

    @Transactional
    public List<RankingDTO> findPontuacaoBySalaId(Integer id) {
        Sala sala = salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Professor professor = professorService.getProfessorLogado();
        if (!sala.getProfessores().contains(professor)) {
            throw new RuntimeException("Acesso negado à sala.");
        }

        return pontuacaoRepository.findPontuacaoBySalaId(id);
    }

    @Autowired
    private SalaJogoRepository salaJogoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Transactional
    public Pontuacao setPontuacao(PontuacaoDTO dto) {

        Optional<Sala> sala = salaRepository.findWithSalaJogosByCodigo(dto.getSalaCod());

        SalaJogo salaJogo = salaJogoRepository
                .findBySalaIdAndJogoId(sala.get().getId(), dto.getJogoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Associação Sala-Jogo não encontrada: sala=" + dto.getSalaCod() + ", jogo=" + dto.getJogoId()));

        Pontuacao pontuacao = new Pontuacao();
        pontuacao.setSalaJogo(salaJogo);
        pontuacao.setData(new Date());
        pontuacao.setAcertos(dto.getAcertos());
        pontuacao.setAluno(dto.getAluno());
        pontuacao.setPontos(dto.getPontos());

        return pontuacaoRepository.save(pontuacao);
    }
}
