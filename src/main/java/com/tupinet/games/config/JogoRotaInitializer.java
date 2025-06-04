package com.tupinet.games.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class JogoRotaInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        try {
            jdbcTemplate.execute("ALTER TABLE jogo ADD COLUMN rota VARCHAR(100) NOT NULL DEFAULT '/'");
        } catch (Exception e) {
            // Ignora erro se a coluna já existe (evita crash em outros devs ou ao reiniciar)
        }

        jdbcTemplate.update("UPDATE jogo SET rota = '/jogoCacaPalavras' WHERE nome = 'Caça-palavras'");
        jdbcTemplate.update("UPDATE jogo SET rota = '/completarPalavra' WHERE nome = 'Palavreca'");
        jdbcTemplate.update("UPDATE jogo SET rota = '/jogo-traducao' WHERE nome = 'Traducao'");
        jdbcTemplate.update("UPDATE jogo SET rota = '/jogo-matematica' WHERE nome = 'Super Cálculo'");
    }
}