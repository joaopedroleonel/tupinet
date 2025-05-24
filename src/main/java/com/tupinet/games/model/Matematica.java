package com.tupinet.games.model;

import java.util.Random;
import java.util.Scanner;

public class Matematica {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numQuestoes = 20;
        int pontuacao = 0;

        System.out.println(" Bem-vindo ao Jogo de Matemática!");
        System.out.println("Responda as seguintes " + numQuestoes + " questões:");

        for (int i = 1; i <= numQuestoes; i++) {
            int a = random.nextInt(10) + 1;
            int b = random.nextInt(10) + 1;
            int operacao = random.nextInt(4); // 0=+, 1=-, 2=*, 3=/

            String pergunta = "";
            int respostaCorreta = 0;

            switch (operacao) {
                case 0:
                    pergunta = a + " + " + b;
                    respostaCorreta = a + b;
                    break;
                case 1:
                    pergunta = a + " - " + b;
                    respostaCorreta = a - b;
                    break;
                case 2:
                    pergunta = a + " * " + b;
                    respostaCorreta = a * b;
                    break;
                case 3:

                    respostaCorreta = a;
                    b = random.nextInt(9) + 1;
                    a = respostaCorreta * b;
                    pergunta = a + " / " + b;
                    break;
            }

            int[] opcoes = new int[4];
            int respostaIndex = random.nextInt(4);
            for (int j = 0; j < 4; j++) {
                if (j == respostaIndex) {
                    opcoes[j] = respostaCorreta;
                } else {
                    int incorreta;
                    do {
                        incorreta = respostaCorreta + random.nextInt(11) - 5;
                    } while (incorreta == respostaCorreta || contem(opcoes, incorreta));
                    opcoes[j] = incorreta;
                }
            }

            System.out.println("\nQuestão " + i + ": Quanto é " + pergunta + "?");
            for (int j = 0; j < 4; j++) {
                System.out.println((j + 1) + ") " + opcoes[j]);
            }

            System.out.print("Escolha a opção correta (1-4): ");
            int escolha = scanner.nextInt();

            if (escolha == respostaIndex + 1) {
                System.out.println(" Correto!");
                pontuacao++;
            } else {
                System.out.println(" Errado! A resposta correta era: " + respostaCorreta);
            }
        }

        System.out.println("\n Fim do jogo! Você acertou " + pontuacao + " de " + numQuestoes + " perguntas.");
        scanner.close();
    }

    private static boolean contem(int[] array, int valor) {
        for (int n : array) {
            if (n == valor) return true;
        }
        return false;
    }
}
