package com.tupinet.games.repository;

import com.tupinet.games.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    boolean existsByCodigo(String codigo);
    Optional<Sala> findByCodigo(String codigo);

    @Override
    @EntityGraph(attributePaths = "jogos")
    List<Sala> findAll();

    @EntityGraph(attributePaths = "jogos")
    Optional<Sala> findWithJogosByCodigo(String codigo);
}