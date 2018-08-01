package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Main {

	private static final int SIRKA_MATICE = 3;
	private static final int VYSKA_MATICE = SIRKA_MATICE;

	public static void main(String[] args) {
		String[] pocatecniStavy = { "724506831", "806547231", "123804765" };
		Map<String, Set<Node>> graf = generujGraf("724506831");
		long size = 0;
		for (Entry<String, Set<Node>> entry : graf.entrySet()) {
			//jedničku přičítám za klíč
			size += entry.getValue().size();
		}
		System.out.println(size);
		for (String pocatecniStav : pocatecniStavy) {
			System.out.printf("Hledání řešení pro počáteční stav %s%n", pocatecniStav);
			List<Node> cestaKeStavu = hledejIterativneDoHloubky(pocatecniStav, "012345678", graf);
			if (cestaKeStavu == null) {
				System.out.printf("Nebylo nalezeno řešení pro počáteční stav %s%n", pocatecniStav);
			} else {
				System.out.printf("Cesta ke stavu má délku %d a je: %n", cestaKeStavu.size());
				for (Node node : cestaKeStavu) {
					System.out.printf("%s -> %n%s%n", node.getAkce(), prevedRadekNaMatici(node.getStav()));
				}
			}
			// zapisDoSouboru("vystup" + pocatecniStav + ".txt", graf);
		}
	}

	private static String prevedRadekNaMatici(String novyStav) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < (novyStav.length() / SIRKA_MATICE); i++) {
			sb.append(novyStav.substring(SIRKA_MATICE * i, (i + 1) * SIRKA_MATICE));
			sb.append("\n");
		}
		return sb.toString();
	}

	private static List<Node> hledejIterativneDoHloubky(String pocatecniStav, String hledanyStav, Map<String, Set<Node>> graf) {
		// Iterative deepening depth-first search
		int maxLimit = graf.size();
		int pocetExpandovanychStavu = 0;
		int pocetVrcholu = 0;
		Node pocatecniNode = new Node(pocatecniStav, null);
		for (int limit = 0; limit <= maxLimit; limit++) {
			Vysledek vysledek = hledejDoHloubkySOmezenim(pocatecniNode, hledanyStav, graf, limit);
			pocetExpandovanychStavu += vysledek.pocetExpandovanychStavu;
			pocetVrcholu = Math.max(pocetVrcholu, vysledek.pocetVrcholu);
			if (vysledek.cesta != null) {
				System.out.printf("Počet expandovaných stavů: %s%nMaximální počet vrcholů uložených do paměti: %s%n", pocetExpandovanychStavu, pocetVrcholu);
				return vysledek.cesta;
			}
		}
		System.out.printf("Počet expandovaných stavů: %s%nMaximální počet vrcholů uložených do paměti: %s%n", pocetExpandovanychStavu, pocetVrcholu);
		return null;
	}

	private static Vysledek hledejDoHloubkySOmezenim(Node pocatecniNode, String hledanyStav, Map<String, Set<Node>> graf, int limit) {
		Deque<Node> stack = new LinkedList<>();
		List<Node> cesta = new ArrayList<>();
		int pocetExpandovanychStavu = 0;
		int pocetVrcholu = 1;
		stack.push(pocatecniNode);
		for (; !stack.isEmpty();) {
			Node aktualniPrvek = stack.pop();
			if (aktualniPrvek.getStav().equals(hledanyStav)) {
				cesta.add(aktualniPrvek);
				pocetVrcholu = Math.max(pocetVrcholu, cesta.size() + stack.size());
				return new Vysledek(cesta, pocetExpandovanychStavu, pocetVrcholu);
			}

			// expanze
			if (aktualniPrvek.hloubka < limit) {
				rekonstruujCestu(aktualniPrvek.hloubka, cesta);
				cesta.add(aktualniPrvek);

				Set<Node> naslednici = graf.get(aktualniPrvek.getStav());
				if (naslednici != null && !naslednici.isEmpty()) {
					pocetExpandovanychStavu += naslednici.size();
					for (Node naslednik : naslednici) {
						if (!cesta.contains(naslednik) && !stack.contains(naslednik)) {
							naslednik.hloubka = aktualniPrvek.hloubka + 1;
							stack.push(naslednik);
						}
					}
					pocetVrcholu = Math.max(pocetVrcholu, cesta.size() + stack.size());
				}
			}
		}
		return new Vysledek(null, pocetExpandovanychStavu, pocetVrcholu);
	}

	private static void rekonstruujCestu(int level, List<Node> path) {
		for (int i = path.size() - 1; i >= 0; i--) {
			if (path.get(i).hloubka >= level) {
				path.remove(i);
			} else if (path.get(i).hloubka < level) {
				return;
			}
		}
	}

	private static Map<String, Set<Node>> generujGraf(String pocatecniStav) {
		System.out.println("Generování grafu...");
		List<String> open = new ArrayList<>(Arrays.asList(pocatecniStav));
		Set<String> closed = new HashSet<>();
		Map<String, Set<Node>> graf = new LinkedHashMap<>();
		long startTotal = System.currentTimeMillis();
		for (int i = 0; !open.isEmpty() && i < open.size(); i++) {
			String stav = open.get(i);
			closed.add(stav);
			List<Node> moznePrechody = generujPrechody(stav);
			if (!graf.containsKey(stav)) {
				graf.put(stav, new LinkedHashSet<>(4));
			}
			List<String> stavyNaslednici = new ArrayList<>(moznePrechody.size());
			for (Node novyStav : moznePrechody) {
				graf.get(stav).add(novyStav);
				stavyNaslednici.add(novyStav.getStav());
			}
			odeberClosed(stavyNaslednici, closed);
			open.addAll(stavyNaslednici);
		}

		System.out.printf("Graf vygenerován. Trvalo to %d ms.%n", System.currentTimeMillis() - startTotal);
		return graf;
	}

	private static List<Node> generujPrechody(String s) {
		List<Node> result = new ArrayList<>();
		int prazdnePole = s.indexOf('0');
		// nahoru
		if (prazdnePole >= SIRKA_MATICE) {
			result.add(new Node(posunPrazdnePole(s, prazdnePole - SIRKA_MATICE), "nahoru"));
		}
		// dolu
		if (prazdnePole < (SIRKA_MATICE * VYSKA_MATICE) - SIRKA_MATICE) {
			result.add(new Node(posunPrazdnePole(s, prazdnePole + SIRKA_MATICE), "dolu"));
		}
		// doprava
		if ((prazdnePole + 1) % SIRKA_MATICE != 0) {
			result.add(new Node(posunPrazdnePole(s, prazdnePole + 1), "doprava"));
		}
		// doleva
		if ((prazdnePole + 1) % SIRKA_MATICE != 1) {
			result.add(new Node(posunPrazdnePole(s, prazdnePole - 1), "doleva"));
		}
		return result;
	}

	private static void odeberClosed(List<String> nasledniciStavy, Set<String> closed) {
		nasledniciStavy.removeAll(closed);
	}

	private static String posunPrazdnePole(String s, int index) {
		return vymenZaPrazdnePole(s, s.charAt(index));
	}

	private static String vymenZaPrazdnePole(String s, char c) {
		return s.replace(c, 'F').replace('0', c).replace('F', '0');
	}

	private static class Node {
		private final String stav;
		private final String akce;
		private int hloubka;

		public Node(String stav, String akce) {
			this.stav = stav;
			this.akce = akce;
		}

		public String getStav() {
			return stav;
		}

		public String getAkce() {
			return akce;
		}

		@Override
		public String toString() {
			return "[novyStav=" + stav + ", akce=" + akce + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((stav == null) ? 0 : stav.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (stav == null) {
				if (other.stav != null)
					return false;
			} else if (!stav.equals(other.stav))
				return false;
			return true;
		}

	}

	private static class Vysledek {
		private final List<Node> cesta;
		private final int pocetExpandovanychStavu;
		private final int pocetVrcholu;

		public Vysledek(List<Node> cesta, int pocetExpandovanychStavu, int pocetVrcholu) {
			this.cesta = cesta;
			this.pocetExpandovanychStavu = pocetExpandovanychStavu;
			this.pocetVrcholu = pocetVrcholu;
		}

	}
}
