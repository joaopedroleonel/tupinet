package com.tupinet.games.service;

import com.tupinet.games.model.Palavra;
import com.tupinet.games.model.Palavreca;
import com.tupinet.games.model.TraducaoPalavra;
import com.tupinet.games.repository.JogoPalavraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PalavrecaService {

    @Autowired
    private JogoPalavraRepository jogoPalavraRepository;

    private final Random random = new Random();

    // conjunto para armazenar palavras já utilizadas
    private Set<String> palavrasUsadas = new HashSet<>();

    @Transactional
    public Palavreca getPalavraAleatoria() {
        // obtém todas as palavras do jogo
        List<Palavra> palavras = jogoPalavraRepository.findPalavrasByJogoId(2);

        Palavra palavra;
        // sorteia uma palavra que ainda não foi usada
        do {
            palavra = palavras.get(random.nextInt(palavras.size()));
        } while (palavrasUsadas.contains(palavra.getTexto()));

        String textoPalavra = palavra.getTexto();

        // marca a palavra como usada
        palavrasUsadas.add(textoPalavra);

        int tamanho = textoPalavra.length();

        // sorteia dois índices distintos para virar lacuna
        // determina a quantidade de letras a ocultar
        int letrasParaOcultar;
        if (tamanho < 7) {
            letrasParaOcultar = 2;
        } else {
            // entre 30% e 40% do tamanho, arredondado, no mínimo 2
            letrasParaOcultar = Math.max(2, (int) Math.round(tamanho * (0.3 + random.nextDouble() * 0.1)));
        }

        // sorteia índices distintos para virar lacunas
        Set<Integer> indices = new HashSet<>();
        while (indices.size() < letrasParaOcultar) {
            int idx = random.nextInt(tamanho);
            if (Character.isLetter(textoPalavra.charAt(idx))) {
                indices.add(idx);
            }
        }


        // cria a versão com lacunas
        StringBuilder lacunas = new StringBuilder(textoPalavra);
        for (int i : indices) {
            lacunas.setCharAt(i, '_');
        }

        // obtém a tradução da palavra
        Optional<String> traducao = palavra.getTraducoes()
                .stream()
                .map(TraducaoPalavra::getTraducao)
                .findFirst();

        // retorna a palavra com lacunas
        return new Palavreca(textoPalavra, lacunas.toString(), indices, traducao.orElse("N/A"));
    }

    public boolean validarResposta(Palavreca palavra, Map<Integer, Character> respostas) {
        for (Map.Entry<Integer, Character> entrada : respostas.entrySet()) {
            if (Character.toLowerCase(palavra.getCompleta().charAt(entrada.getKey())) !=
                    Character.toLowerCase(entrada.getValue())) {
                return false;
            }
        }
        return true;
    }
}
