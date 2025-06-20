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

    // Conjunto para armazenar palavras já utilizadas (agora é thread-safe)
    private Set<String> palavrasUsadas = Collections.synchronizedSet(new HashSet<>());

    @Transactional
    public Palavreca getPalavraAleatoria() {
        // Obtém todas as palavras do jogo
        List<Palavra> palavras = jogoPalavraRepository.findPalavrasByJogoId(2);

        // Verifica se ainda há palavras disponíveis
        if (palavrasUsadas.size() >= palavras.size()) {
            throw new IllegalStateException("Todas as palavras já foram utilizadas");
        }

        Palavra palavra;
        int tentativas = 0;
        int maxTentativas = palavras.size() * 2; // Evita loop infinito

        // Sorteia uma palavra que ainda não foi usada
        do {
            palavra = palavras.get(random.nextInt(palavras.size()));
            tentativas++;

            // Se após muitas tentativas não encontrar, limpa as usadas
            if (tentativas > maxTentativas) {
                resetPalavrasUsadas();
                palavra = palavras.get(random.nextInt(palavras.size()));
                break;
            }
        } while (palavrasUsadas.contains(palavra.getTexto()));

        String textoPalavra = palavra.getTexto();
        palavrasUsadas.add(textoPalavra);

        // Cria a palavra com lacunas
        return criarPalavraComLacunas(palavra, textoPalavra);
    }

    private Palavreca criarPalavraComLacunas(Palavra palavra, String textoPalavra) {
        int tamanho = textoPalavra.length();
        int letrasParaOcultar = calcularLetrasParaOcultar(tamanho);
        Set<Integer> indices = selecionarIndicesParaOcultar(textoPalavra, letrasParaOcultar);

        // Cria a versão com lacunas
        StringBuilder lacunas = new StringBuilder(textoPalavra);
        for (int i : indices) {
            lacunas.setCharAt(i, '_');
        }

        // Obtém a tradução
        Optional<String> traducao = palavra.getTraducoes()
                .stream()
                .map(TraducaoPalavra::getTraducao)
                .findFirst();

        return new Palavreca(textoPalavra, lacunas.toString(), indices, traducao.orElse("N/A"));
    }

    private int calcularLetrasParaOcultar(int tamanhoPalavra) {
        if (tamanhoPalavra < 7) {
            return 2;
        }

        double percentual = 0.3 + random.nextDouble() * 0.1;
        return Math.max(2, (int) Math.round(tamanhoPalavra * percentual));
    }

    private Set<Integer> selecionarIndicesParaOcultar(String textoPalavra, int quantidade) {
        Set<Integer> indices = new HashSet<>();
        while (indices.size() < quantidade) {
            int idx = random.nextInt(textoPalavra.length());
            if (Character.isLetter(textoPalavra.charAt(idx))) {
                indices.add(idx);
            }
        }
        return indices;
    }

    public boolean validarResposta(Palavreca palavra, Map<Integer, Character> respostas) {
        if (palavra == null || respostas == null) {
            return false;
        }

        for (Map.Entry<Integer, Character> entrada : respostas.entrySet()) {
            if (entrada.getKey() < 0 || entrada.getKey() >= palavra.getCompleta().length()) {
                continue;
            }

            if (Character.toLowerCase(palavra.getCompleta().charAt(entrada.getKey())) !=
                    Character.toLowerCase(entrada.getValue())) {
                return false;
            }
        }
        return true;
    }

    public void resetPalavrasUsadas() {
        palavrasUsadas.clear();
    }
}