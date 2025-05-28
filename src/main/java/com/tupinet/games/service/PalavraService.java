package com.tupinet.games.service;

import com.tupinet.games.model.Palavra;
import com.tupinet.games.model.PalavraJogoCompletarP;
import com.tupinet.games.model.TraducaoPalavra;
import com.tupinet.games.repository.JogoPalavraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PalavraService {

    @Autowired
    private JogoPalavraRepository jogoPalavraRepository;

    private final Random random = new Random();

    @Transactional
    public PalavraJogoCompletarP getPalavraAleatoria() {
        List<Palavra> palavras = jogoPalavraRepository.findPalavrasByJogoId(2);
        Palavra p = palavras.get(random.nextInt(palavras.size()));

        String palavra = p.getTexto();

        int tamanho = palavra.length();

        // Sorteia dois índices distintos para virar lacuna
        Set<Integer> indices = new HashSet<>();
        while (indices.size() < 2) {
            int idx = random.nextInt(tamanho);
            if (Character.isLetter(palavra.charAt(idx))) {
                indices.add(idx);
            }
        }

        // Cria a versão com lacunas
        StringBuilder lacunas = new StringBuilder(palavra);
        for (int i : indices) {
            lacunas.setCharAt(i, '_');
        }

        Optional<String> traducao = p.getTraducoes()
                .stream()
                .map(TraducaoPalavra::getTraducao)
                .findFirst();

        return new PalavraJogoCompletarP(palavra, lacunas.toString(), indices, traducao.get());

    }

    public boolean validarResposta(PalavraJogoCompletarP palavra, Map<Integer, Character> respostas) {
        for (Map.Entry<Integer, Character> entrada : respostas.entrySet()) {
            if (Character.toLowerCase(palavra.getCompleta().charAt(entrada.getKey())) !=
                    Character.toLowerCase(entrada.getValue())) {
                return false;
            }
        }
        return true;
    }
}
