/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author olda9
 */
public class SirkaVlakno extends Thread {

    List<Stav> path;
    Queue<Stav> open;
    Queue<Stav> closed;
    Stav pocatek;
    Stav cil;
    boolean nalezeno = false;
    boolean chcipni = false;

    @Override
    public void run() {
        nalezeno = false;
        chcipni = false;
        breadthFirstSearch(pocatek, cil);
    }

    public List<Stav> breadthFirstSearch(Stav pocatek, Stav cil) {

        int c=0;
        while (!open.isEmpty() && !chcipni) {
            Stav aktualni = open.remove();
            closed.offer(aktualni);
            if (++c%10000==0) {
                System.out.println(c);
                System.out.println(open.size());
                System.out.println(path.size());
                System.out.println(path.size()+open.size());
                System.out.println("*********");
            }
            ArrayList<Stav> nasledniciList = aktualni.getNaslednikyList();
            for (Stav s : nasledniciList) {

                path.add(s);
                path.add(aktualni);

                if (s.equals(cil)) {
                    //return processPath(source, destination, path);
                    return path;
                } else {
                    if (!closed.contains(s) && !open.contains(s)) {
                        open.offer(s);
                    }
                }
            }
        }
        return null;
    }

    public List<Stav> getPath() {
        return path;
    }

    public void setPath(List<Stav> path) {
        this.path = path;
    }

    public Queue<Stav> getOpen() {
        return open;
    }

    public void setOpen(Queue<Stav> open) {
        this.open = open;
    }

    public Queue<Stav> getClosed() {
        return closed;
    }

    public void setClosed(Queue<Stav> closed) {
        this.closed = closed;
    }

    void chcipni() {
        chcipni = true;
    }

    public Stav getPocatek() {
        return pocatek;
    }

    public void setPocatek(Stav pocatek) {
        this.pocatek = pocatek;
    }

    public Stav getCil() {
        return cil;
    }

    public void setCil(Stav cil) {
        this.cil = cil;
    }

}
