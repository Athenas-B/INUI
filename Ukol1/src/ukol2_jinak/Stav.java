/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol2_jinak;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author olda9
 */
public class Stav implements Serializable {

    private static final long serialVersionUID = 1L;

    private String data;
    private int poziceDiry;
    private String akce;
    private int hloubka;
    private boolean navstiveno = false;
    private double f, g;

    public Stav() {
//        this.data = "724506831";
//        this.poziceDiry = 5;
        this.hloubka = 0;
    }

    public boolean muzeNahoru() {
        return poziceDiry - 3 >= 0;
    }

    public boolean muzeDolu() {
        return poziceDiry + 3 <= 8;
    }

    public boolean muzeDoleva() {

        return (poziceDiry >= 1 && poziceDiry <= 2) || (poziceDiry >= 4 && poziceDiry <= 5) || (poziceDiry >= 7 && poziceDiry <= 8);
    }

    public boolean muzeDoprava() {
        return (poziceDiry >= 0 && poziceDiry <= 1) || (poziceDiry >= 3 && poziceDiry <= 4) || (poziceDiry >= 6 && poziceDiry <= 7);
    }

    public Stav Nahoru() {
        if (muzeNahoru()) {
            Stav novy = new Stav();
            novy.poziceDiry = this.poziceDiry - 3;
            char vymena = this.data.charAt(novy.poziceDiry);
            novy.data = this.data.replace(vymena, 'F').replace('0', vymena).replace('F', '0');
            novy.akce = "nahoru";
            return novy;
        } else {
            throw new IndexOutOfBoundsException("Nelze posunout nahoru");
        }
    }

    public Stav Dolu() {
        if (muzeDolu()) {
            Stav novy = new Stav();
            novy.poziceDiry = this.poziceDiry + 3;
            char vymena = this.data.charAt(novy.poziceDiry);
            novy.data = this.data.replace(vymena, 'F').replace('0', vymena).replace('F', '0');
            novy.akce = "dolu";
            return novy;
        } else {
            throw new IndexOutOfBoundsException("Nelze posunout dolu");
        }
    }

    public Stav Doleva() {
        if (muzeDoleva()) {
            Stav novy = new Stav();
            novy.poziceDiry = this.poziceDiry - 1;
            char vymena = this.data.charAt(novy.poziceDiry);
            novy.data = this.data.replace(vymena, 'F').replace('0', vymena).replace('F', '0');
            novy.akce = "doleva";
            return novy;
        } else {
            throw new IndexOutOfBoundsException("Nelze posunout doleva");
        }
    }

    public Stav Doprava() {
        if (muzeDoprava()) {
            Stav novy = new Stav();
            novy.poziceDiry = this.poziceDiry + 1;
            char vymena = this.data.charAt(novy.poziceDiry);
            novy.data = this.data.replace(vymena, 'F').replace('0', vymena).replace('F', '0');
            novy.akce = "doprava";
            return novy;
        } else {
            throw new IndexOutOfBoundsException("Nelze posunout doprava");
        }
    }

    public String getSmer(Stav cil) {
        if (muzeDoleva()) {
            Stav novy = Doleva();
            if (novy.equals(cil)) {
                return "Doleva";
            }
        }
        if (muzeDolu()) {
            Stav novy = Dolu();
            if (novy.equals(cil)) {
                return "Dolu";
            }
        }
        if (muzeDoprava()) {
            Stav novy = Doprava();
            if (novy.equals(cil)) {
                return "Doprava";
            }
        }
        if (muzeNahoru()) {
            Stav novy = Nahoru();
            if (novy.equals(cil)) {
                return "Nahoru";
            }
        }
        return null;

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stav other = (Stav) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPoziceDiry(int poziceDiry) {
        this.poziceDiry = poziceDiry;
    }

    public int getHloubka() {
        return hloubka;
    }

    public void setHloubka(int hloubka) {
        this.hloubka = hloubka;
    }

    @Override
    public String toString() {
        return "Stav{" + "data=" + data + ", akce=" + akce + '}';
    }

    public Stav[] getNasledniky() {

        return getNaslednikyList().toArray(new Stav[4]);
    }

    public ArrayList<Stav> getNaslednikyList() {
        ArrayList<Stav> seznam = new ArrayList<>(4);
        if (muzeDoleva()) {
            seznam.add(Doleva());
        }
        if (muzeDoprava()) {
            seznam.add(Doprava());
        }
        if (muzeDolu()) {
            seznam.add(Dolu());
        }
        if (muzeNahoru()) {
            seznam.add(Nahoru());
        }
        return seznam;
    }

    public Stav getRodice() {
        if (akce == null) {
            return null;
        }
        switch (akce) {
            case "doleva":
                return Doprava();
            case "doprava":
                return Doleva();
            case "dolu":
                return Nahoru();
            case "nahoru":
                return Dolu();

            default:
                return null;
        }
    }

    public boolean isNavstiveno() {
        return navstiveno;
    }

    public void setNavstiveno(boolean navstiveno) {
        this.navstiveno = navstiveno;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    private int expandovano = 0;
    private int max = 0;
    private int cls = 0;

    private List<Stav> sirka(Stav pocatek, Stav cil) {
        Set<Stav> closed = new HashSet<>();
        Queue<Stav> fronta = new LinkedList<>();
        LinkedList<Stav> cesta = new LinkedList<>();
        fronta.add(pocatek);
        //expandovano = 0;
        //max = fronta.size();
        while (!fronta.isEmpty()) {
            Stav aktualni = fronta.remove();
            closed.add(aktualni);
//            if (closed.size() % 10000 == 0) {
//                System.out.println(closed.size());
//            }
            if (aktualni.equals(cil)) {
                //cesta.add(aktualni);
                max = fronta.size();
                cls = closed.size();
                return vratCestu(closed, aktualni, cesta);
                //  } else if (cesta.size() >= limit) {
                //       return null;
            } else {
                //rekonstruujCestu(aktualni.getHloubka(), cesta);
                //cesta.add(aktualni);
                for (Stav stav : aktualni.getNaslednikyList()) {
                    ++expandovano;
                    if (!closed.contains(stav) && !fronta.contains(stav)) {
                        fronta.add(stav);
                    }
                }
                max = fronta.size();
            }
        }
        cls = closed.size();
        max = fronta.size();
        return cesta;
    }

    private List<Stav> vratCestu(Set<Stav> closed, Stav cil, LinkedList<Stav> cesta) {

        cesta.addFirst(cil);
        if (cil == null) {
            return cesta;
        }
        Stav rodic = cil.getRodice();
        if (rodic == null) {
            return cesta;
        }
        for (Stav stav : closed) {
            if (stav.equals(rodic)) {
                return vratCestu(closed, stav, cesta);

            }
        }
        return cesta;
    }

    public static void main(String[] args) {
        Stav s1 = new Stav();
        s1.setData("724506831");
        s1.setPoziceDiry(4);
        Stav s2 = new Stav();
        s2.setData("806547231");
        s2.setPoziceDiry(1);
        Stav s3 = new Stav();
        s3.setData("123804765");
        s3.setPoziceDiry(4);

        Stav c = new Stav();
        c.setData("012345678");
        c.setPoziceDiry(0);

        System.out.println("Hledání cesty algoritmem do šířky ze stavu " + s1);
        List<Stav> p1 = s1.sirka(s1, c);
        System.out.println("Řešení po " + p1.size() + " krocích při " + s1.max + " stavech ve frontě a " + s1.cls + " v closed za expandovani " + s1.expandovano + " stavů:");
        for (Stav stav : p1) {
            System.out.println(stav);
        }
        System.out.println("*********************");
        System.out.println("Hledání cesty algoritmem do šířky ze stavu " + s2);
        List<Stav> p2 = s2.sirka(s2, c);
        System.out.println("Řešení po " + p2.size() + " krocích při " + s2.max + " stavech ve frontě a " + s2.cls + " v closed za expandovani " + s2.expandovano + " stavů:");
        for (Stav stav : p2) {
            System.out.println(stav);
        }
        System.out.println("*********************");
        System.out.println("Hledání cesty algoritmem do šířky ze stavu " + s3);
        List<Stav> p3 = s3.sirka(s3, c);
        System.out.println("Řešení po " + p3.size() + " krocích při " + s3.max + " stavech ve frontě a " + s3.cls + " v closed za expandovani " + s3.expandovano + " stavů:");
        for (Stav stav : p3) {
            System.out.println(stav);
        }
        System.out.println("*********************");

        try (ObjectOutputStream oos
                = new ObjectOutputStream(new FileOutputStream("p.ser"))) {

            oos.writeObject(p1);
            oos.writeObject(p2);
            oos.writeObject(p3);
            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
