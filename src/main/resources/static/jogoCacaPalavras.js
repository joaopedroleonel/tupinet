const grid = document.getElementById('word-search');
const letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

//tirar depois, são "todas" as palavras do banco
const responseAll = [
    {
        id: 1,
        word: "SAPO",
        pt: "Sapo",
        guarani: "Kururu",
        position: [61, 72, 83, 94]
    },
    {
        id: 2,
        word: "ARCO",
        pt: "Arco",
        guarani: "Yvyrapã",
        position: [48, 58, 68, 78]
    },
    {
        id: 3,
        word: "LARANJA",
        pt: "Laranja",
        guarani: "Pire'y",
        position: [21,22,23,24,25,26,27]
    },
];

//escolhendo uma palavra aleatoria
function getRandomItem(arr) {
    return arr[Math.floor(Math.random() * arr.length)];
}
let response = getRandomItem(responseAll);


//gerador de letras aleatorias
for (let i = 0; i < 100; i++) {
    const cell = document.createElement('div');
    cell.classList.add('cell');
    cell.id = i;
    cell.setAttribute('clicked', 'false')
    cell.textContent = letters[Math.floor(Math.random() * letters.length)];
    cell.add
    grid.appendChild(cell);

    //clicar na letra
    cell.addEventListener('click', () => {
        if (!cell.classList.contains('clicked')){
            cell.classList.add('clicked');
        }else{
            cell.classList.remove('clicked');
        }
    })
}

//escrevendo palavra
writeInScreen()

function writeInScreen(){

    const word = response.word;
    const position = response.position;
    for(let i = 0; i < word.length; i++){
        document.getElementById(position[i]).innerText = word[i];
    }
    document.getElementById('target-word').innerText = response.pt;
}

//score
let score = 0;
function updateScore() {
    score += 10;
    document.getElementById('score').innerText = score;
}

// resetar o jogo

function resetGame() {
    for (let i = 0; i < 100; i++) {
        const cell = document.getElementById(i);
        cell.classList.remove('clicked');

        cell.textContent = letters[Math.floor(Math.random() * letters.length)];
    }

    response = getRandomItem(responseAll);
    writeInScreen();
    document.getElementById('target-word').innerText = response.pt;
}


//botao de enviar

const submitButton = document.querySelector('.submitButton');
submitButton.addEventListener('click', () => {
    var clickedLetters = document.querySelectorAll('.clicked');
    clickedLetters = Array.from(clickedLetters).map(cell => parseInt(cell.id));
    response.position = response.position.sort((a, b) => a - b)

    console.log(clickedLetters);
    console.log(response.position);
    var correctAnwser = false
    for (let i = 0; i < response.word.length; i++) {

        if (clickedLetters[i] == response.position[i]) {
            correctAnwser = true;
        }else{
            correctAnwser = false;
            i = 101;
        }
    }
    if (correctAnwser){
        for (let i = 0; i < clickedLetters.length; i++) {

            if (clickedLetters[i] == response.position[i]) {
                correctAnwser = true;
            }else{
                correctAnwser = false;
                i = 101;
            }
        }
    }

    if (correctAnwser) {
        alert('acertou :)');
        updateScore();
        resetGame();
    }else{
        alert('errou viu bixo :(');
    }
});