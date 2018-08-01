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
public class Klic {

    private Stav pocatek;
    private String akce;

    public Klic(Stav pocatek, String akce) {
        this.pocatek = pocatek;
        this.akce = akce;

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

    @Override
    public String toString() {
        return "Klic{" + "pocatek=" + pocatek + ", akce=" + akce + ", }";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.pocatek.hashCode();
        hash = 89 * hash + this.akce.hashCode();
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
        final Klic other = (Klic) obj;
        if (!this.akce.equalsIgnoreCase(other.akce)) {
            return false;
        }
        if (!this.pocatek.equals(other.pocatek)) {
            return false;
        }
        return true;
    }

}
