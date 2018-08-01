/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author olda9
 */
public class Stavy_listem_sirka3 {

    /**
     * @param args the command line arguments
     */
    private Stav pocatecni;
    private LinkedList<Stav> open;
    private HashSet<Stav> closed;
    private LinkedHashSet<Prechod> naslednici;

    public Stavy_listem_sirka3() {
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

    public List<Stav> hledejReseni(Stav pocatek, Stav cil, HashMap<Stav, ArrayList<Stav>> graf) {
        if (!graf.containsKey(cil) || !graf.containsKey(pocatek)) {
            return null;
        }


            List<Stav> cesta = sirka(pocatek, cil, graf);

        return null;
    }
    private int expandovano = 0;
    private int max = 0;

    private List<Stav> sirka(Stav pocatek, Stav cil, HashMap<Stav, ArrayList<Stav>> graf) {
        LinkedList<Stav> zasobnik = new LinkedList<>();
        ArrayList<Stav> cesta = new ArrayList<>();
        zasobnik.addLast(pocatek);
        expandovano = 0;
        max = zasobnik.size() + cesta.size();
        while (!zasobnik.isEmpty()) {
            Stav aktualni = zasobnik.removeFirst();
            //zasobnikAkci.removeLast();
            closed.add(aktualni);
            if (aktualni.equals(cil)) {
                cesta.add(aktualni);
                max = zasobnik.size() + cesta.size();
                return cesta;
                //  } else if (cesta.size() >= limit) {
                //       return null;
            } else  {
                rekonstruujCestu(aktualni.getHloubka(), cesta);
                cesta.add(aktualni);
                naslednik(graf, aktualni, zasobnik, cesta);
                max = zasobnik.size() + cesta.size();
            }
        }
        return null;
    }

    private static void rekonstruujCestu(int level, List<Stav> path) {
        for (int i = path.size() - 1; i >= 0; i--) {
            if (path.get(i).getHloubka() >= level) {
                path.remove(i);
            } else if (path.get(i).getHloubka() < level) {
                return;
            }
        }
    }

    private void naslednik(HashMap<Stav, ArrayList<Stav>> graf, Stav aktualni, LinkedList<Stav> zasobnik, List<Stav> cesta) {
        ArrayList<Stav> list;
        if ((list = graf.get(aktualni)) != null) {
            for (Stav naslednik : list) {
                if (!cesta.contains(naslednik) && !zasobnik.contains(naslednik)) {
                    zasobnik.addLast(naslednik);
                    expandovano++;
                    //zasobnikAkci.addLast(smer);
                    naslednik.setHloubka(aktualni.getHloubka() + 1);
                    //cesta.addLast(new Prechod(aktualni, "Doleva", novy));
                }
            }
        }

    }

    public int getExpandovano() {
        return expandovano;
    }

    public int getMax() {
        return max;
    }

    public static void main(String[] args) {
        
        Stavy_listem_sirka3 s = new Stavy_listem_sirka3();
        long startTime = System.nanoTime();
        //System.out.println(seconds.toSeconds(startTime));
        s.Generuj();
       // System.out.println(seconds.toSeconds(System.nanoTime() - startTime));
        startTime = System.nanoTime();
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
                ArrayList<Stav> list = new ArrayList<>();
                list.add(prechod.getVysledekAkce());
                graf.put(prechod.getPocatek(), list);
            }
        }
        //System.out.println(seconds.toSeconds(System.nanoTime() - startTime));
        startTime = System.nanoTime();
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

        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek1 + " do " + cil);
        //System.out.println(seconds.toSeconds(System.nanoTime() - startTime));
        startTime = System.nanoTime();
        List<Stav> reseni = s.hledejReseni(pocatek1, cil, graf);
        if (reseni != null) {
            System.out.println("Pocet urovni: " + reseni.size());
            System.out.println("Cesta stavy: \n" + reseni);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni.size(); i++) {
                instrukce.add(reseni.get(i - 1).getSmer(reseni.get(i)));
            }

            System.out.println("Cesta instrukcemi: \n" + instrukce);
            System.out.println("Stavu expandovano: " + s.getExpandovano());
            System.out.println("Maximalne v pameti: " + s.getMax());
        } else {
            System.out.println("Nema reseni!");
        }
        //System.out.println(seconds.toSeconds(System.nanoTime() - startTime));
        startTime = System.nanoTime();
        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek2 + " do " + cil);
        
        List<Stav> reseni2 = s.hledejReseni(pocatek2, cil, graf);
        if (reseni2 != null) {
            System.out.println("Pocet urovni: " + reseni2.size());
            System.out.println("Cesta stavy: \n" + reseni2);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni2.size(); i++) {
                instrukce.add(reseni2.get(i - 1).getSmer(reseni2.get(i)));
            }
            System.out.println("Cesta instrukcemi: \n" + instrukce);
            System.out.println("Stavu expandovano: " + s.getExpandovano());
            System.out.println("Maximalne v pameti: " + s.getMax());
        } else {
            System.out.println("Nema reseni!");
        }
        //System.out.println(seconds.toSeconds(System.nanoTime() - startTime));
        startTime = System.nanoTime();
        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek3 + " do " + cil);
        
        List<Stav> reseni3 = s.hledejReseni(pocatek3, cil, graf);
        if (reseni3 != null) {
            System.out.println("Pocet urovni: " + reseni3.size());
            System.out.println("Cesta stavy: \n" + reseni3);
            ArrayList<String> instrukce = new ArrayList<>();
            for (int i = 1; i < reseni3.size(); i++) {
                instrukce.add(reseni3.get(i - 1).getSmer(reseni3.get(i)));
            }
            System.out.println("Cesta instrukcemi: \n" + instrukce);
            System.out.println("Stavu expandovano: " + s.getExpandovano());
            System.out.println("Maximalne v pameti: " + s.getMax());
        } else {
            System.out.println("Nema reseni!");
        }
        //System.out.println(seconds.toSeconds(System.nanoTime() - startTime));


    }
}
