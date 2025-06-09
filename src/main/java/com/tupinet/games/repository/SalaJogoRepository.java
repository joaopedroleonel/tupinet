package com.tupinet.games.repository;

import com.tupinet.games.model.SalaJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SalaJogoRepository extends JpaRepository<SalaJogo, Long> {
    Optional<SalaJogo> findBySalaIdAndJogoId(Integer salaId, Integer jogoId);
}