const perguntasPorNivel = {
    facil: 8,
    medio: 12,
    dificil: 12 // Alterado de 15 para 12
};

let nivelAtual = "facil";
let questoes = [];
let indiceAtual = 0;
let respostaUsuario = "";
let score = 0;
let respostasUsuario = [];
let cronometroInterval = null;
let msInicio = null;
let msFinal = null;

/* GERA AS PERGUNTAS */
function gerarPerguntas(nivel) {
    const perguntas = [];
    if (nivel === "facil") {
        let opcoes = ["+", "-"], min = 0, max = 20, qtd = perguntasPorNivel[nivel];
        for (let i = 0; i < qtd; i++) {
            let op = opcoes[Math.floor(Math.random() * opcoes.length)];
            let a, b, question, resposta;
            if (op === "+") {
                a = Math.floor(Math.random() * (max - min + 1)) + min;
                b = Math.floor(Math.random() * (max - min + 1)) + min;
                question = `${a} + ${b} =`;
                resposta = a + b;
            } else {
                a = Math.floor(Math.random() * (max - min + 1)) + min;
                b = Math.floor(Math.random() * (a - min + 1)) + min;
                question = `${a} - ${b} =`;
                resposta = a - b;
            }
            perguntas.push({ question, resposta: resposta.toString() });
        }
    } else if (nivel === "medio") {
        let qtd = perguntasPorNivel[nivel];
        for (let i = 0; i < qtd; i++) {
            let tipo = Math.random();
            let question, resposta;
            if (tipo < 0.4) {
                let a = Math.floor(Math.random() * (99 - 20 + 1)) + 20;
                let b = Math.floor(Math.random() * ((a - 20) + 1)) + 20;
                question = `${a} - ${b} =`;
                resposta = a - b;
            } else if (tipo < 0.7) {
                let a = Math.floor(Math.random() * (99 - 20 + 1)) + 20;
                let b = Math.floor(Math.random() * (99 - 20 + 1)) + 20;
                question = `${a} + ${b} =`;
                resposta = a + b;
            } else {
                let a = 3;
                let b = Math.floor(Math.random() * 10) + 1;
                question = `${a} x ${b} =`;
                resposta = a * b;
            }
            perguntas.push({ question, resposta: resposta.toString() });
        }
    } else if (nivel === "dificil") {
        let qtd = perguntasPorNivel[nivel];
        for (let i = 0; i < qtd; i++) {
            let tipo = Math.random();
            let question, resposta;
            if (tipo < 0.3) {
                let a = Math.floor(Math.random() * (999 - 99 + 1)) + 99;
                let b = Math.floor(Math.random() * (999 - 99 + 1)) + 99;
                question = `${a} + ${b} =`;
                resposta = a + b;
            } else if (tipo < 0.6) {
                let a = Math.floor(Math.random() * (999 - 99 + 1)) + 99;
                let b = Math.floor(Math.random() * ((a - 99) + 1)) + 99;
                question = `${a} - ${b} =`;
                resposta = a - b;
            } else if (tipo < 0.8) {
                let a = Math.floor(Math.random() * 10) + 1;
                let b = Math.floor(Math.random() * 10) + 1;
                question = `${a} x ${b} =`;
                resposta = a * b;
            } else {
                let b = Math.floor(Math.random() * 9) + 2;
                let respostaInt = Math.floor(Math.random() * 100) + 2;
                let a = respostaInt * b;
                question = `${a} ÷ ${b} =`;
                resposta = respostaInt;
            }
            perguntas.push({ question, resposta: resposta.toString() });
        }
    }
    return perguntas;
}

function atualizarQuestao() {
    document.body.classList.remove("final");
    if (!questoes[indiceAtual]) return;
    const questaoAtual = questoes[indiceAtual];
    document.querySelector('.questao-atual').textContent = `Questão ${indiceAtual + 1} de ${questoes.length}`;
    const respostaOculta = respostaUsuario.padEnd(questaoAtual.resposta.length, "_");
    document.querySelector('.calculo-central').textContent = questaoAtual.question + " " + respostaOculta;
}

function iniciaCronometro() {
    msInicio = Date.now();
    msFinal = null;
    if (cronometroInterval) clearInterval(cronometroInterval);
    cronometroInterval = setInterval(() => {
        let msAgora = msFinal ? msFinal : Date.now();
        let ms = msAgora - msInicio;
        document.querySelector('.cronometro').textContent = formatarTempo(ms);
    }, 250);
}

function pararCronometro() {
    msFinal = Date.now();
    if (cronometroInterval) clearInterval(cronometroInterval);
    document.querySelector('.cronometro').textContent = formatarTempo(msFinal - msInicio);
}

function formatarTempo(ms) {
    let s = Math.floor(ms / 1000);
    let min = Math.floor(s / 60);
    let sec = s % 60;
    return `${min.toString().padStart(2, "0")}:${sec.toString().padStart(2, "0")}`;
}

function reiniciarQuiz(nivel) {
    nivelAtual = nivel;
    questoes = gerarPerguntas(nivel);
    indiceAtual = 0;
    respostaUsuario = "";
    score = 0;
    respostasUsuario = [];
    document.querySelector(".score-exibicao").textContent = "";
    document.querySelector('.calculo-central').classList.remove("correto", "errado");
    document.querySelector('.calculo-central').textContent = "";
    document.querySelector('.cronometro').textContent = "00:00";
    atualizarQuestao();
    iniciaCronometro();
    document.body.classList.remove("final");
    document.querySelectorAll(".nivel-btn").forEach(btn => btn.classList.remove('active'));
    document.querySelector(`.nivel-btn[data-nivel="${nivel}"]`).classList.add('active');
}

function responder(numero) {
    if (!questoes[indiceAtual]) return;
    const respostaEsperada = questoes[indiceAtual].resposta;
    if (respostaUsuario.length < respostaEsperada.length) {
        respostaUsuario += numero;
        atualizarQuestao();
    }
}

function apagar() {
    respostaUsuario = "";
    atualizarQuestao();
}

function enviar() {
    if (!questoes[indiceAtual]) return;
    if (respostaUsuario === "") {
        // feedback opcional (alerta, animação, etc)
        const calculo = document.querySelector('.calculo-central');
        calculo.classList.remove("correto", "errado");
        calculo.classList.add("errado");
        calculo.textContent = "Digite sua resposta!";
        setTimeout(() => {
            atualizarQuestao();
            calculo.classList.remove("errado");
        }, 1000);
        return; // Não prossegue!
    }

    const respostaEsperada = questoes[indiceAtual].resposta;
    const acertou = respostaUsuario === respostaEsperada;
    respostasUsuario.push({
        question: questoes[indiceAtual].question,
        respostaEsperada,
        respostaUsuario,
        acertou
    });
    if (acertou) {
        score += 10;
        showFeedback("Correto!", true);
    } else {
        showFeedback("Errou!", false);
    }
}

function showFeedback(texto, positivo) {
    const calculo = document.querySelector('.calculo-central');
    calculo.textContent = texto;
    calculo.classList.remove("correto", "errado");
    calculo.classList.add(positivo ? "correto" : "errado");
    setTimeout(() => {
        proximaQuestao();
    }, 1000);
}

function proximaQuestao() {
    indiceAtual++;
    respostaUsuario = "";
    document.querySelector('.calculo-central').classList.remove("correto", "errado");
    if (indiceAtual >= questoes.length) {
        apresentarResumoFinal();
        return;
    }
    atualizarQuestao();
}

// NOVA FUNÇÃO DE RESUMO FINAL: DIVIDE EM DUAS TABELAS LADO A LADO
function apresentarResumoFinal() {
    pararCronometro();
    document.body.classList.add("final"); // Esconde botoes-e-quadrado e numeros-painel
    document.querySelector('.calculo-central').textContent = "";
    document.querySelector('.questao-atual').textContent = "";
    let htmlResumo = `<strong>Parabéns!</strong><br>
    ${score} pontos<br>
    Tempo total: ${formatarTempo(msFinal - msInicio)}<br>
    Você acertou ${score/10}/${questoes.length} questões!<br><br>
    <strong>Respostas:</strong><br>`;

    // Divide em duas tabelas de mesmo tamanho
    const metade = Math.ceil(respostasUsuario.length / 2);
    const tabela1 = respostasUsuario.slice(0, metade);
    const tabela2 = respostasUsuario.slice(metade);

    htmlResumo += `<div class="tabelas-finais-duplas">`;

    [tabela1, tabela2].forEach(grupo => {
        htmlResumo += `<table class="resumo-tabela">
          <tr>
            <th>Questão</th>
            <th>Sua resposta</th>
            <th>Certa?</th>
            <th>Correta</th>
          </tr>`;
        grupo.forEach(q => {
            htmlResumo += `<tr class="${q.acertou ? 'acerto' : 'erro'}">
                <td>${q.question}</td>
                <td>${q.respostaUsuario || '--'}</td>
                <td>${q.acertou ? '✔️' : '❌'}</td>
                <td>${q.respostaEsperada}</td>
            </tr>`;
        });
        htmlResumo += `</table>`;
    });

    htmlResumo += `</div>`;
    document.querySelector(".score-exibicao").innerHTML = htmlResumo;
}

document.querySelectorAll(".numero-quadrado").forEach(btn =>
    btn.addEventListener("click", () => responder(btn.textContent))
);

document.querySelector(".botao-apagar").addEventListener("click", apagar);
document.querySelector(".botao-enviar").addEventListener("click", enviar);

document.querySelectorAll(".nivel-btn").forEach(btn =>
    btn.addEventListener("click", () => reiniciarQuiz(btn.dataset.nivel))
);

reiniciarQuiz("facil");