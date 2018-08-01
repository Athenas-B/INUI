/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author olda9
 */
public class Stavy_listem_AStar {

    /**
     * @param args the command line arguments
     */
    private Stav pocatecni;
    private LinkedList<Stav> open;
    private HashSet<Stav> closed;
    private LinkedHashSet<Prechod> naslednici;

    public Stavy_listem_AStar() {
        pocatecni = new Stav();
        open = new LinkedList<>();
        closed = new HashSet<>();
        naslednici = new LinkedHashSet<>();
    }

    public LinkedList<Stav> getPath(Stav start, Stav exit) {

        LinkedList<Stav> foundPath = new LinkedList<Stav>();
        LinkedList<Stav> opensList = new LinkedList<Stav>();
        LinkedList<Stav> closedList = new LinkedList<Stav>();
        Hashtable<Stav, Integer> gscore = new Hashtable<Stav, Integer>();
        Hashtable<Stav, Stav> cameFrom = new Hashtable<Stav, Stav>();
        Stav x = new Stav();
        gscore.put(start, 0);
        opensList.add(start);
        while (!opensList.isEmpty()) {
            int min = -1;
            //searching for minimal score
            for (Stav f : opensList) {
                if (min == -1) {
                    min = gscore.get(f) + f.getVzdalenostH(exit);
                    x = f;
                } else {
                    int currf = gscore.get(f) + f.getVzdalenostH(exit);
                    if (min > currf) {
                        min = currf;
                        x = f;
                    }
                }
            }
            if (x == exit) {
                //path reconstruction
                Stav curr = exit;
                while (curr != start) {
                    foundPath.addFirst(curr);
                    curr = cameFrom.get(curr);
                }
                return foundPath;
            }
            opensList.remove(x);
            closedList.add(x);
            for (Stav y : x.getNasledniky()) {
                int tentGScore = gscore.get(x) + x.getVzdalenostH(y);
                boolean distIsBetter = false;
                if (!opensList.contains(y)) {
                    opensList.add(y);
                    distIsBetter = true;
                } else if (tentGScore < gscore.get(y)) {
                    distIsBetter = true;
                }
                if (distIsBetter) {
                    cameFrom.put(y, x);
                    gscore.put(y, tentGScore);
                }
            }
        }

        return foundPath;
    }

     public LinkedList<Stav> getPath2(Stav start, Stav exit) {

        LinkedList<Stav> foundPath = new LinkedList<Stav>();
        LinkedList<Stav> opensList = new LinkedList<Stav>();
        LinkedList<Stav> closedList = new LinkedList<Stav>();
        Hashtable<Stav, Integer> gscore = new Hashtable<Stav, Integer>();
        Hashtable<Stav, Stav> cameFrom = new Hashtable<Stav, Stav>();
        Stav current = new Stav();
        gscore.put(start, 0);
        opensList.add(start);
        while (!opensList.isEmpty()) {
            int min = -1;
            //searching for minimal score
            for (Stav f : opensList) {
                if (min == -1) {
                    min = gscore.get(f) + f.getVzdalenostH(exit);
                    current = f;
                } else {
                    int currf = gscore.get(f) + f.getVzdalenostH(exit);
                    if (min > currf) {
                        min = currf;
                        current = f;
                    }
                }
            }
            if (current == exit) {
                //path reconstruction
                Stav curr = exit;
                while (curr != start) {
                    foundPath.addFirst(curr);
                    curr = cameFrom.get(curr);
                }
                return foundPath;
            }
            opensList.remove(current);
            closedList.add(current);
            for (Stav y : current.getNasledniky()) {
                int tentGScore = gscore.get(current) + 1;
                boolean distIsBetter = false;
                if (!opensList.contains(y)) {
                    opensList.add(y);
                    distIsBetter = true;
                } else if (tentGScore < gscore.get(y)) {
                    distIsBetter = true;
                }
                if (distIsBetter) {
                    cameFrom.put(y, current);
                    gscore.put(y, tentGScore);
                }
            }
        }

        return foundPath;
    }

    
    public int getExpandovano() {
        return 0;//expandovano;
    }

    public int getMax() {
        return 0;//max;
    }

    public static void main(String[] args) {
        Stavy_listem_AStar s = new Stavy_listem_AStar();
       
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

        List<Stav> reseni = s.getPath2(pocatek1, cil);
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

        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek2 + " do " + cil);

        List<Stav> reseni2 = s.getPath(pocatek2, cil);
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

        System.out.println("");
        System.out.println("Hledam cestu v grafu z " + pocatek3 + " do " + cil);

        List<Stav> reseni3 = s.getPath(pocatek3, cil);
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

    }
}
