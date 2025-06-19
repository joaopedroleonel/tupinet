package com.tupinet.games.repository;

import com.tupinet.games.DTO.PalavraComTraducaoDTO;
import com.tupinet.games.model.Palavra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PalavraRepository extends JpaRepository<Palavra, Integer> {
    @Query("SELECT new com.tupinet.games.DTO.PalavraComTraducaoDTO(p.id, p.texto, t.traducao) " +
            "FROM Palavra p LEFT JOIN p.traducoes t")
    List<PalavraComTraducaoDTO> findPalavrasComTraducoes();
}
