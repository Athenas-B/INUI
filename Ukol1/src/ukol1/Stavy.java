/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 *
 * @author olda9
 */
public class Stavy {

    /**
     * @param args the command line arguments
     */
    private Stav pocatecni;
    private LinkedList<Stav> open;
    private HashSet<Stav> closed;
    private LinkedHashSet<Prechod> naslednici;

    public Stavy() {
        pocatecni = new Stav();
        open = new LinkedList<>();
        closed = new HashSet<>();
        naslednici = new LinkedHashSet<>();
    }

    public void Generuj() {
        //open.add(pocatecni);
        Stav aktualni = pocatecni;
        Stav novy = pocatecni;
        open.add(pocatecni);

        while (!open.isEmpty()) {
            aktualni = open.removeFirst();
            closed.add(aktualni);

            if (aktualni.muzeDoleva()) {
                novy = aktualni.Doleva();
                if (closed.contains(novy)) {
                    naslednici.add(new Prechod(aktualni, "Doleva", novy));
                }//duplicita
                else {
                    open.add(novy);
                    naslednici.add(new Prechod(aktualni, "Doleva", novy));
                }
            }
            if (aktualni.muzeDolu()) {
                novy = aktualni.Dolu();
                if (closed.contains(novy)) {
                    naslednici.add(new Prechod(aktualni, "Dolu", novy));
                }//duplicita
                else {
                    open.add(novy);
                    naslednici.add(new Prechod(aktualni, "Dolu", novy));
                }
            }
            if (aktualni.muzeDoprava()) {
                novy = aktualni.Doprava();
                if (closed.contains(novy)) {
                    naslednici.add(new Prechod(aktualni, "Doprava", novy));
                }//duplicita
                else {
                    open.add(novy);
                    naslednici.add(new Prechod(aktualni, "Doprava", novy));
                }
            }
            if (aktualni.muzeNahoru()) {
                novy = aktualni.Nahoru();
                if (closed.contains(novy)) {
                    naslednici.add(new Prechod(aktualni, "Nahoru", novy));
                }//duplicita
                else {
                    open.add(novy);
                    naslednici.add(new Prechod(aktualni, "Nahoru", novy));
                }
            }

        }

    }

    public LinkedList<Prechod> hledejReseni(Stav pocatek, Stav cil, HashMap<Klic, Stav> graf) {
        if (!graf.containsValue(cil) || !graf.containsValue(pocatek)) {
            return null;
        }

        for (int limit = 1; limit < 43; limit++) {//graf.size()
            LinkedList<Prechod> cesta = hloubka(pocatek, cil, graf, limit);
            if (cesta != null) {
                return cesta;
            }
        }
        return null;
    }

    private LinkedList<Prechod> hloubka(Stav pocatek, Stav cil, HashMap<Klic, Stav> graf, int limit) {
        HashSet<Stav> closed = new HashSet<>();
        LinkedList<Prechod> cesta = new LinkedList<>();
        LinkedList<Stav> zasobnik = new LinkedList<>();
        LinkedList<String> zasobnikAkci = new LinkedList<>();
        zasobnik.addLast(pocatek);
        zasobnikAkci.addLast("Start");

        while (!zasobnik.isEmpty()) {
            Stav aktualni = zasobnik.removeLast();
            zasobnikAkci.removeLast();
            closed.add(aktualni);
            if (aktualni.equals(cil)) {
                return cesta;
            } else if (cesta.size() >= limit) {
                return null;
            } else if (aktualni.getHloubka() >= limit) {

            } else {

                naslednik(graf, aktualni, "Doleva", closed, zasobnik, zasobnikAkci);
                naslednik(graf, aktualni, "Doprava", closed, zasobnik, zasobnikAkci);
                naslednik(graf, aktualni, "Dolu", closed, zasobnik, zasobnikAkci);
                naslednik(graf, aktualni, "Donahoru", closed, zasobnik, zasobnikAkci);
            }

        }
        return null;
    }

    private void naslednik(HashMap<Klic, Stav> graf, Stav aktualni, String smer, HashSet<Stav> closed1, LinkedList<Stav> zasobnik, LinkedList<String> zasobnikAkci) {
        Stav novy;
        if ((novy = graf.get(new Klic(aktualni, smer))) != null) {
            if (!closed1.contains(novy)) {
                zasobnik.addLast(novy);
                zasobnikAkci.addLast(smer);
                novy.setHloubka(aktualni.getHloubka() + 1);
                //cesta.addLast(new Prechod(aktualni, "Doleva", novy));
            }
        }
    }

    public static void main(String[] args) {
        Stavy s = new Stavy();
        s.Generuj();
        /* ukol1:
        System.out.println(s.naslednici.size());
        Stav hledany = ((Prechod) s.naslednici.toArray()[105301]).getVysledekAkce();
        System.out.println(hledany);
        for (Object s1 : s.naslednici.toArray()) {
            if (((Prechod) s1).getPocatek().equals(hledany)) {
                System.out.println(s1);
            }
        }
         */
        HashMap<Klic, Stav> graf = new HashMap<>(180440);
        for (Prechod prechod : s.naslednici) {
            graf.put(new Klic(prechod.getPocatek(), prechod.getAkce()), prechod.getVysledekAkce());
        }

        Stav pocatek1 = new Stav();
        int[][] d1 = {{7, 2, 4}, {5, 0, 6}, {8, 3, 1}};
        pocatek1.setData(d1);
        pocatek1.setPoziceDiry(new int[]{1, 1});
        Stav pocatek2 = new Stav();
        int[][] d2 = {{8, 0, 6}, {5, 4, 7}, {2, 3, 1}};
        pocatek2.setData(d2);
        pocatek2.setPoziceDiry(new int[]{1, 0});
        Stav pocatek3 = new Stav();
        int[][] d3 = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
        pocatek3.setData(d3);
        pocatek3.setPoziceDiry(new int[]{1, 1});
        Stav cil = new Stav();
        int[][] d4 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        cil.setData(d4);
        cil.setPoziceDiry(new int[]{0, 0});

        System.out.println(s.hledejReseni(pocatek1, cil, graf));
    }

}
