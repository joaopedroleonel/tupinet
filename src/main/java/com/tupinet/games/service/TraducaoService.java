package com.tupinet.games.service;

import com.tupinet.games.model.Palavra;
import com.tupinet.games.model.TraducaoPalavra;
import com.tupinet.games.repository.JogoPalavraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TraducaoService {

    @Autowired
    private JogoPalavraRepository jogoPalavraRepository;

    @Transactional
    public Optional<Map<String, String>> getPalavraAleatoriaComTraducao() {

        List<Palavra> palavras = jogoPalavraRepository.findPalavrasByJogoId(1);

        if (palavras.isEmpty()) {
            return Optional.empty();
        }

        Palavra palavra = palavras.get(new Random().nextInt(palavras.size()));

        Optional<String> traducao = palavra.getTraducoes()
                .stream()
                .map(TraducaoPalavra::getTraducao)
                .findFirst();

        return traducao.map(t -> {
            Map<String, String> resultado = new HashMap<>();
            resultado.put("palavra", palavra.getTexto());
            resultado.put("traducao", t);
            return resultado;
        });

    }
}
