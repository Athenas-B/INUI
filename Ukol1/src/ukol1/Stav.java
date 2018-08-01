/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author olda9
 */
public class Stav {

    private int[][] data;
    private int[] poziceDiry;
    private int hloubka;
    private boolean navstiveno = false;
    private double f, g;

    public Stav() {
        this.data = new int[][]{{7, 2, 4}, {5, 0, 6}, {8, 3, 1}};
        this.poziceDiry = new int[]{1, 1};
        this.hloubka = 0;
    }

    public boolean muzeNahoru() {
        return poziceDiry[1] >= 1;
    }

    public boolean muzeDolu() {
        return poziceDiry[1] <= 1;
    }

    public boolean muzeDoleva() {
        return poziceDiry[0] >= 1;
    }

    public boolean muzeDoprava() {
        return poziceDiry[0] <= 1;
    }

    public Stav Nahoru() {
        Stav novy = new Stav();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                novy.data[i][j] = data[i][j];

            }
        }

        //        novy.setData(data.);
        int[] poziceNoveDiry = {poziceDiry[0], poziceDiry[1] - 1};
        novy.setPoziceDiry(poziceNoveDiry);

//        novy.data[poziceDiry[0]][poziceDiry[1]] = data[poziceNoveDiry[0]][poziceNoveDiry[1]];
//        novy.data[poziceNoveDiry[0]][poziceNoveDiry[1]] = 0;
        novy.data[poziceDiry[1]][poziceDiry[0]] = data[poziceNoveDiry[1]][poziceNoveDiry[0]];
        novy.data[poziceNoveDiry[1]][poziceNoveDiry[0]] = 0;

        return novy;
    }

    public Stav Dolu() {
        Stav novy = new Stav();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                novy.data[i][j] = data[i][j];

            }
        }
        int[] poziceNoveDiry = {poziceDiry[0], poziceDiry[1] + 1};
        novy.setPoziceDiry(poziceNoveDiry);

        novy.data[poziceDiry[1]][poziceDiry[0]] = data[poziceNoveDiry[1]][poziceNoveDiry[0]];
        novy.data[poziceNoveDiry[1]][poziceNoveDiry[0]] = 0;

        return novy;
    }

    public Stav Doleva() {
        Stav novy = new Stav();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(data[i], 0, novy.data[i], 0, 3);
        }
        int[] poziceNoveDiry = {poziceDiry[0] - 1, poziceDiry[1]};
        novy.setPoziceDiry(poziceNoveDiry);

        novy.data[poziceDiry[1]][poziceDiry[0]] = data[poziceNoveDiry[1]][poziceNoveDiry[0]];
        novy.data[poziceNoveDiry[1]][poziceNoveDiry[0]] = 0;

        return novy;
    }

    public Stav Doprava() {
        Stav novy = new Stav();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                novy.data[i][j] = data[i][j];

            }
        }
        int[] poziceNoveDiry = {poziceDiry[0] + 1, poziceDiry[1]};

        novy.data[poziceDiry[1]][poziceDiry[0]] = data[poziceNoveDiry[1]][poziceNoveDiry[0]];
        novy.data[poziceNoveDiry[1]][poziceNoveDiry[0]] = 0;

        novy.setPoziceDiry(poziceNoveDiry);

//        novy.data[poziceDiry[0]][poziceDiry[1]] = data[poziceNoveDiry[0]][poziceNoveDiry[1]];
//        novy.data[poziceNoveDiry[0]][poziceNoveDiry[1]] = 0;
        return novy;
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

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public int[] getPoziceDiry() {
        return poziceDiry;
    }

    public void setPoziceDiry(int[] poziceDiry) {
        this.poziceDiry = poziceDiry;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Arrays.deepHashCode(this.data);
        //hash = 17 * hash + Arrays.hashCode(this.poziceDiry);
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
        if (!Arrays.deepEquals(this.data, other.data)) {
            return false;
        }
//        if (!Arrays.equals(this.poziceDiry, other.poziceDiry)) {
//            return false;
//        }
        return true;
    }

    public int getHloubka() {
        return hloubka;
    }

    public void setHloubka(int hloubka) {
        this.hloubka = hloubka;
    }

    @Override
    public String toString() {
        return "Stav{" + "data=["
                + data[0][0] + "," + data[0][1] + "," + data[0][2] + "] "
                + "[" + data[1][0] + "," + data[1][1] + "," + data[1][2] + "] "
                + "[" + data[2][0] + "," + data[2][1] + "," + data[2][2] + "] "
                + '}';
    }

    public int getVzdalenostH(Stav cil) {
        int vzdalenost = 0;
        for (int radek = 0; radek < data.length; radek++) {
            for (int sloupec = 0; sloupec < data[radek].length; sloupec++) {
                for (int radekC = 0; radekC < cil.data.length; radekC++) {
                    for (int sloupecC = 0; sloupecC < cil.data[radekC].length; sloupecC++) {
                        if (data[radek][sloupec] == cil.data[radekC][sloupecC]) {
                            vzdalenost += Math.abs(radek - radekC) + Math.abs(sloupec - sloupecC);
                        }
                    }
                }
            }
        }
        return vzdalenost;
    }

    public Stav[] getNasledniky() {
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
        return seznam.toArray(new Stav[seznam.size()]);
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
    

}
