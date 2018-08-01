/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.Objects;

/**
 *
 * @author olda9
 */
public class Prechod {

    private Stav pocatek;
    private String akce;
    private Stav vysledekAkce;

    public Prechod(Stav pocatek, String akce, Stav vysledekAkce) {
        this.pocatek = pocatek;
        this.akce = akce;
        this.vysledekAkce = vysledekAkce;
    }

    public String getAkce() {
        return akce;
    }

    public void setAkce(String akce) {
        this.akce = akce;
    }

    public Stav getPocatek() {
        return pocatek;
    }

    public void setPocatek(Stav pocatek) {
        this.pocatek = pocatek;
    }

    public Stav getVysledekAkce() {
        return vysledekAkce;
    }

    public void setVysledekAkce(Stav vysledekAkce) {
        this.vysledekAkce = vysledekAkce;
    }

    @Override
    public String toString() {
        return "Prechod{" + "pocatek=" + pocatek + ", akce=" + akce + ", vysledekAkce=" + vysledekAkce + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.pocatek);
        hash = 89 * hash + Objects.hashCode(this.akce);
        hash = 89 * hash + Objects.hashCode(this.vysledekAkce);
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
        final Prechod other = (Prechod) obj;
        if (!this.akce.equals(other.akce)) {
            return false;
        }
        if (!this.pocatek.equals(other.pocatek)) {
            return false;
        }
        if (!this.vysledekAkce.equals(other.vysledekAkce)) {
            return false;
        }

        return true;
    }

}
