import java.util.ArrayList;

public class Baralho {
	/*
	 * Baralho do jogo de Escova possui apenas 40 cartas, pois as cartas com
	 * valor 8, 9 e 10 de todos os naipes são descartadas.
	 */
	private static String[] naipes = { "Copas", "Espadas", "Ouros", "Paus" }; // Naipes da carta possíveis
	private static String[] valores = { "As", "2", "3", "4", "5", "6", "7", "Q", "J", "K" }; // Valores da carta possíveis
	private ArrayList<Carta> cartas; // Cartas do baralho

	public Baralho() {
		cartas = new ArrayList<Carta>();
		int id = 1;
		// Cria o baralho, combinando todos os naipes e valores
		for (int j = 0; j < valores.length; j++) {
			for (int k = 0; k < naipes.length; k++) {
				cartas.add(new Carta(id, valores[j], naipes[k], j + 1));
				id++;
			}
		}
	}

	public ArrayList<Carta> getCartas() {
		return this.cartas;
	}

}
