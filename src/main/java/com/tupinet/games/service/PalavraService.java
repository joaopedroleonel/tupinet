package com.tupinet.games.service;

import com.tupinet.games.model.PalavraJogo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PalavraService {

    private final List<String> palavras = Arrays.asList(
            "Árvore", "Cavalo", "Peixe", "Feliz"
    );

    private final Random random = new Random();

    public PalavraJogo getPalavraAleatoria() {
        String palavra = palavras.get(random.nextInt(palavras.size()));
        int tamanho = palavra.length();

        // Sorteia dois índices distintos para virar lacuna
        Set<Integer> indices = new HashSet<>();
        while (indices.size() < 2) {
            int idx = random.nextInt(tamanho);
            // Evita lacunar espaço em branco ou acentos (como "Á")
            if (Character.isLetter(palavra.charAt(idx))) {
                indices.add(idx);
            }
        }

        // Cria a versão com lacunas
        StringBuilder lacunas = new StringBuilder(palavra);
        for (int i : indices) {
            lacunas.setCharAt(i, '_');
        }

        return new PalavraJogo(palavra, lacunas.toString(), indices);
    }

    public boolean validarResposta(PalavraJogo palavra, Map<Integer, Character> respostas) {
        for (Map.Entry<Integer, Character> entrada : respostas.entrySet()) {
            if (Character.toLowerCase(palavra.getCompleta().charAt(entrada.getKey())) !=
                    Character.toLowerCase(entrada.getValue())) {
                return false;
            }
        }
        return true;
    }
}
