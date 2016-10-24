## Escova

Jogo de baralho Escova desenvolvido utilizando API de Sockets do Java.
Jogado em rede por duas pessoas.

### Baralho
Baralho composto por 40 cartas, pois eliminam-se os 8, 9 e 10 de todos os naipes e também os curingas de um baralho.

Cada carta possui uma pontuação: 
- Rei vale 10;  K
- Valete vale 09;   J
- Dama vale 08;  Q
- Ás vale 01; A
- Demais cartas valem o próprio número.

Estes valores só tem significado para se somar 15 pontos com outras cartas,
conforme descrito no item Descrição. A contagem de pontos do jogo será feita de forma diferente,
como também será visto no item Contabilização dos pontos.

### Descrição

O jogo inicia no momento em que dois jogadores (clientes) se conectam ao jogo (servidor).
O servidor embaralha o baralho e distribui 4 cartas na mesa, que são exibidas para todos os jogadores e 3 cartas para o primeiro jogador, que foi o primeiro a se conectar. As cartas que sobram são mantidas no baralho.
O jogador deverá procurar uma carta em sua mão que somada a uma ou mais das cartas da mesa dê um total de 15. Se o jogador conseguir pegar todas as cartas restantes na mesa de uma única vez ele fez uma escova.
Caso ele não consiga pegar nenhuma carta da mesa, deve simplesmente descartar na mesa uma das cartas de sua mão.
Após efetuar sua jogada, a vez é do segundo jogador, que recebe 3 cartas e deve fazer o mesmo.
Quando os jogadores tiverem utilizado suas 3 cartas, uma nova mão de 3 cartas é distribuída. 
A partida prossegue da mesma maneira até que o monte de cartas termine. Quando terminar, os pontos de cada jogador são calculados e se nenhum deles atingiu 15 pontos o jogador inicial é intercalado e uma nova rodada inicia, dentro do mesmo sistema anterior, até que algum dos jogadores atinja 15 pontos. Se o número de pontos de ambos ultrapassar 15 ganha quem possuir o maior número de pontos.

### Contabilização dos pontos

Escova - cada uma, vale 1 ponto;
Maioria de cartas - vale 1 ponto;
Maioria de ouros - vale 1 ponto;
Todas as cartas de ouros - vale 2 pontos;
Posse do 7 de ouros, também conhecido por 7 belo - vale 1 ponto.

_Trabalho desenvolvido para a disciplina de Redes de Computadores I (UNISINOS)._
