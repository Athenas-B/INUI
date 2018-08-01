/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author olda9
 */
public class Stavy_listem_sirka2 {

    /**
     * @param args the command line arguments
     */
    private Stav pocatecni;
    private LinkedList<Stav> open;
    private HashSet<Stav> closed;
    private LinkedHashSet<Prechod> naslednici;

    public Stavy_listem_sirka2() {
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

    private int expandovano = 0;
    private int max = 0;

    public List<Stav> breadthFirstSearch(HashMap<Stav, ArrayList<Stav>> graf, Stav pocatek, Stav cil) {
        List<Stav> path = Collections.synchronizedList(new ArrayList<>(100));

        if (!graf.containsKey(cil) || !graf.containsKey(pocatek)) {
            return null;
        }
        if (pocatek.equals(cil)) {
            path.add(pocatek);
            return path;
        }

        Queue<Stav> open = new ConcurrentLinkedQueue<>();
        Queue<Stav> closed = new ConcurrentLinkedQueue<>();

        open.offer(pocatek);
        int vlaken = 1;
        List<SirkaVlakno> vlakna = new ArrayList<>(vlaken);
        for (int i = 0; i < vlaken; i++) {
            SirkaVlakno v = new SirkaVlakno();
            v.setClosed(closed);
            v.setOpen(open);
            v.setPath(path);
            v.setCil(cil);
            v.setPocatek(pocatek);
            vlakna.add(v);
        }
        boolean nalezeno = false;
        while (!nalezeno) {
            for (SirkaVlakno v : vlakna) {
                if (!v.isAlive() && v.nalezeno) {
                    nalezeno = true;
                    break;
                }
                if (open.size() >= 1 && !v.isAlive()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Stavy_listem_sirka2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    v.start();
                }
            }
        }
        for (SirkaVlakno v : vlakna) {
            if (v.isAlive()) {
                v.chcipni();
            }
        }
        if (nalezeno) {
            return path;
        }  
        return null;
    }

    /**
     * Adds the nodes involved in the shortest path.
     *
     * @param src The source node.
     * @param destination The destination node.
     * @param path The path that has nodes and their neighbours.
     * @return The shortest path.
     */
    

    public static void main(String[] args) {

        Stavy_listem_sirka2 s = new Stavy_listem_sirka2();
        s.Generuj();

        // ukol1:
        System.out.println(s.naslednici.size());
        Stav hledany = ((Prechod) s.naslednici.toArray()[105301]).getVysledekAkce();
        System.out.println(hledany);

        for (Prechod prechod : s.naslednici) {
            if (prechod.getPocatek().equals(hledany)) {
                System.out.println(prechod);
            }
        }

        HashMap<Stav, ArrayList<Stav>> graf = new HashMap<>(181440);
        for (Prechod prechod : s.naslednici) {
            //graf.put(new Klic(prechod.getPocatek(), prechod.getAkce()), prechod.getVysledekAkce());
            if (graf.containsKey(prechod.getPocatek())) {
                ArrayList<Stav> get = graf.get(prechod.getPocatek());
                get.add(prechod.getVysledekAkce());
            } else {
                ArrayList<Stav> list = new ArrayList<Stav>();
                list.add(prechod.getVysledekAkce());
                graf.put(prechod.getPocatek(), list);
            }
        }
        System.out.println("Velikost grafu: " + graf.size());

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

        System.out.println("Hledaní v grafu metodou do šířky:");
        System.out.println("Hledam cestu v grafu z " + pocatek1 + " do " + cil);

        List<Stav> reseni = s.breadthFirstSearch(graf, pocatek1, cil);
        if (reseni != null) {
            System.out.println("Pocet urovni: " + reseni.size());
            System.out.println("Cesta stavy: \n" + reseni);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni.size(); i++) {
                instrukce.add(reseni.get(i - 1).getSmer(reseni.get(i)));
            }

            System.out.println("Cesta instrukcemi: \n" + instrukce);
            //System.out.println("Stavu expandovano: " + Stavy_listem_sirka2.expandovano_2);
            //System.out.println("Maximalne v pameti: " + Stavy_listem_sirka2.max_2);
        } else {
            System.out.println("Nema reseni!");
        }

        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek2 + " do " + cil);

        List<Stav> reseni2 = s.breadthFirstSearch(graf, pocatek2, cil);
        if (reseni2 != null) {
            System.out.println("Pocet urovni: " + reseni2.size());
            System.out.println("Cesta stavy: \n" + reseni2);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni2.size(); i++) {
                instrukce.add(reseni2.get(i - 1).getSmer(reseni2.get(i)));
            }
            System.out.println("Cesta instrukcemi: \n" + instrukce);
            //System.out.println("Stavu expandovano: " + Stavy_listem_sirka2.expandovano_2);
            //System.out.println("Maximalne v pameti: " + Stavy_listem_sirka2.max_2);
        } else {
            System.out.println("Nema reseni!");
        }

        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek3 + " do " + cil);

        List<Stav> reseni3 = s.breadthFirstSearch(graf, pocatek3, cil);
        if (reseni3 != null) {
            System.out.println("Pocet urovni: " + reseni3.size());
            System.out.println("Cesta stavy: \n" + reseni3);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni3.size(); i++) {
                instrukce.add(reseni3.get(i - 1).getSmer(reseni3.get(i)));
            }
            System.out.println("Cesta instrukcemi: \n" + instrukce);
            // System.out.println("Stavu expandovano: " + Stavy_listem_sirka2.expandovano_2);
            //System.out.println("Maximalne v pameti: " + Stavy_listem_sirka2.max_2);
        } else {
            System.out.println("Nema reseni!");
        }

    }
}
