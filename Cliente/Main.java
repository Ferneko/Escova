import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		String ip = "127.0.0.1";
		if (args.length > 0) {
			ip = args[0];
		}
		
		Socket socket = new Socket(ip, 6789);
		
		ObjectOutputStream paraServidor = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream doServidor = new ObjectInputStream(socket.getInputStream());
		Scanner scanner = new Scanner(System.in);
		String nome, idsCartasMesa;
		int opcao, idCartaMao;
		boolean cartaValida = false;
		Jogador vencedor = null;

		// Recebe o nome do jogador
		System.out.println("Digite seu nome:");
		nome = scanner.nextLine();
		// Cria um objeto jogador a partir do seu nome
		Jogador jogador = new Jogador(nome);

		// Envia o objeto criado para o servidor
		paraServidor.reset();
		paraServidor.writeObject(jogador);
		while (doServidor.readObject().equals("OK")) {
			ArrayList<Carta> mesa = (ArrayList<Carta>) doServidor.readObject();
			System.out.println("Mesa:");
			System.out.println(mesa);

			jogador = (Jogador) doServidor.readObject();

			ArrayList<Carta> mao = jogador.getMao();

			System.out.println("Suas cartas:");
			System.out.println(mao);
			boolean jogou = false;
			do {
				do {
					System.out.println("Sua vez. Digite 1 para efetuar uma jogada e 2 para descartar uma carta:");
					opcao = Integer.parseInt(scanner.nextLine());
				} while (opcao != 1 && opcao != 2);

				Carta cartaMao = null;
				ArrayList<Carta> cartasMesa = new ArrayList<Carta>();

				if (opcao == 1) { // Jogada de 15 pontos
					do {
						do {
							System.out.println("Digite o ID da carta da mão a ser jogada:");
							idCartaMao = Integer.parseInt(scanner.nextLine());
							for (Carta carta : mao) {
								if (idCartaMao == carta.getId()) {
									cartaMao = carta;
									cartaValida = true;
									break;
								}
							}
						} while (!cartaValida);

						cartasMesa = new ArrayList<Carta>();
						cartaValida = false;
						System.out
								.println("Digite os IDs das cartas da mesa a serem capturadas, separados por espaço:");
						idsCartasMesa = scanner.nextLine();

						for (String idCartaMesa : idsCartasMesa.split(" ")) {
							int id = Integer.parseInt(idCartaMesa);
							for (Carta carta : mesa) {
								if (id == carta.getId()) {
									cartaValida = true;
									cartasMesa.add(carta);
									break;
								}
							}
						}
					} while (!cartaValida);

					Jogada jogada = new Jogada(cartaMao, cartasMesa, mao, mesa);
					if (jogada.efetuarJogada()) {
						System.out.println(
								"Cartas capturadas. Aguarde a jogada de " + jogador.getAdversario().getNome() + ".");
						paraServidor.reset();
						paraServidor.writeObject(jogada);
						jogou = true;
					} else
						System.out.println("Jogada inválida, pois não somou 15! Tente novamente.");
				} else { // Descarte
					do {
						System.out.println("Digite o ID da carta a ser descartada:");
						idCartaMao = Integer.parseInt(scanner.nextLine());
						for (Carta carta : mao) {
							if (idCartaMao == carta.getId()) {
								cartaMao = carta;
								cartaValida = true;
								break;
							}
						}
					} while (!cartaValida);
					Jogada jogada = new Jogada(cartaMao, cartasMesa, mao, mesa);
					jogada.efetuarJogada();
					System.out.println(
							"Carta descartada. Aguarde a jogada de " + jogador.getAdversario().getNome() + ".");
					paraServidor.reset();
					paraServidor.writeObject(jogada);
					jogou = true;
				}
			} while (!jogou);
		}

		if (jogador.venceu())
			vencedor = jogador;
		else {
			vencedor = jogador.getAdversario();
		}

		System.out.println("FIM DE JOGO!");
		System.out.println(vencedor.getNome() + " VENCEU!");
		System.out.println("\nPlacar final:");
		System.out.println(vencedor.getNome() + ": " + vencedor.getPontos() + " pontos.");
		System.out.println(vencedor.getAdversario().getNome() + ": " + vencedor.getAdversario().getPontos() + " pontos.");

		scanner.close();
		socket.close();
	}
}
