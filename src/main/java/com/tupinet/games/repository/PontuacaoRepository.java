package com.tupinet.games.repository;

import com.tupinet.games.DTO.PontuacaoDTO;
import com.tupinet.games.DTO.RankingDTO;
import com.tupinet.games.model.Pontuacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PontuacaoRepository extends JpaRepository<Pontuacao, Integer> {
    @Query("SELECT new com.tupinet.games.DTO.RankingDTO(p.aluno, p.acertos, p.pontos, j.nome) " +
            "FROM Pontuacao p " +
            "JOIN p.salaJogo sj " +
            "JOIN sj.jogo j " +
            "JOIN sj.sala s " +
            "WHERE s.id = :salaId")
    List<RankingDTO> findPontuacaoBySalaId(@Param("salaId") Integer salaId);
}