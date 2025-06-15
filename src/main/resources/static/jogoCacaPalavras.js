const grid = document.getElementById('word-search');
const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const url = "http://localhost:8080/apicacapalavras";

let score = 0;
let tentativas = 1;
let tempoRestante = 300;
let cronometroAtivo = false;
let intervaloCronometro;

window.addEventListener('load', async () => {
    let siteResponse = await carregarPalavraDoBanco();
    iniciarJogo(siteResponse);
});

// Função para buscar a palavra do banco
async function carregarPalavraDoBanco() {
    const requisicao = await fetch(url);
    const siteResponse = await requisicao.json();
    return siteResponse;
}

window.addEventListener('load', async () => {
    let siteResponse = await carregarPalavraDoBanco();
    iniciarJogo(siteResponse);
    iniciarCronometro();
});

//alertas de acertar e errar
function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.textContent = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 2500);
}

// finalizacao de jogo
function finalizarJogo() {
    document.getElementById('game-over-message').innerText = "Fim de Jogo!";
    document.getElementById('final-score').innerText = `Parabéns, você fez ${score} pontos!`;
    document.getElementById('game-over-screen').style.display = 'flex';
    fetch('/salvar-pontuacao', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            jogoId: sessionStorage.getItem('jogoId'),
            salaCod: sessionStorage.getItem('codigoSala'),
            aluno: sessionStorage.getItem('nomeAluno'),
            pontos: score,
            acertos: score/10
        })
    });
}

function voltarParaInicio() {
    window.location.href = "/";
}

//cronometro

function iniciarCronometro() {
    if (cronometroAtivo) return;

    cronometroAtivo = true;
    const tempoElemento = document.getElementById('timer');

    intervaloCronometro = setInterval(() => {
        tempoRestante--;
        tempoElemento.innerText = `${tempoRestante}`;

        if (tempoRestante <= 0) {
            clearInterval(intervaloCronometro);
            alert("acabou o tempo");
            finalizarJogo();
        }
    }, 1000);
}

// Função principal que inicializa o jogo
    function iniciarJogo(siteResponse) {
        let palavra = siteResponse.palavra.toUpperCase();
        let posicao = siteResponse.posicao.split(',').map(Number);

        // Limpa o grid antes de gerar novamente (em caso de reset)
        grid.innerHTML = '';

        // Gera o grid com letras aleatórias
        for (let i = 0; i < 100; i++) {
            const cell = document.createElement('div');
            cell.classList.add('cell');
            cell.id = i;
            cell.setAttribute('clicked', 'false');
            cell.textContent = letters[Math.floor(Math.random() * letters.length)];
            grid.appendChild(cell);

            cell.addEventListener('click', () => {
                cell.classList.toggle('clicked');
            });
        }

        // Insere a palavra nas posições corretas
        for (let i = 0; i < palavra.length; i++) {
            const cell = document.getElementById(posicao[i]);
            if (cell) {
                cell.textContent = palavra[i];
            } else {
                console.warn(`Posição inválida: ${posicao[i]}`);
            }
        }

        // Exibe a palavra em português para o jogador procurar
        document.getElementById('target-word').innerText = palavra;

        //pontuacao atualizando e somando
        function atualizarPontuacao() {
            score += 10;
            document.getElementById('score').innerText = `${score}`;
        }

        //atualizar cacapalavras
        async function reiniciarJogo() {
            try {
                const novaPalavra = await carregarPalavraDoBanco();
                iniciarJogo(novaPalavra);
            } catch (error) {
                console.error("Erro ao reiniciar o jogo:", error);
            }
        }

        // Configura botão de enviar
        const submitButton = document.querySelector('.submitButton');
        submitButton.onclick = () => {
            let letrasClicadas = Array.from(document.querySelectorAll('.clicked')).map(cell => parseInt(cell.id));
            letrasClicadas.sort((a, b) => a - b);
            posicao.sort((a, b) => a - b);

            let acertou = JSON.stringify(letrasClicadas) === JSON.stringify(posicao);

            if (acertou) {
                showToast('Acertou!', 'success');
                atualizarPontuacao();
            } else {
                showToast('Errou!', 'error');
            }

            tentativas++;
            document.getElementById('tentativas').innerText = tentativas;

            if (tentativas > 10) {
                finalizarJogo();

            } else {
                reiniciarJogo();
            }
        };
    }