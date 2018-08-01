/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ukol4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

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
    //private LinkedHashSet<Prechod> naslednici;

    Queue<Stav> openQueue;
    Set<Stav> closedList;
    int expandovano;

    public Stavy_listem_AStar() {
        pocatecni = new Stav();
        open = new LinkedList<>();
        closed = new HashSet<>();
        // naslednici = new LinkedHashSet<>();
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
            for (Stav y : x.getNaslednikyList()) {
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
            for (Stav y : current.getNaslednikyList()) {
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

    /**
     * The main A Star Algorithm in Java.
     *
     * finds an allowed path from start to goal coordinates on this map.
     * <p>
     * This method uses the A Star algorithm. The hCosts value is calculated in
     * the given Node implementation.
     * <p>
     * This method will return a LinkedList containing the start node at the
     * beginning followed by the calculated shortest allowed path ending with
     * the end node.
     * <p>
     * If no allowed path exists, an empty list will be returned.
     * <p>
     * <p>
     * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
     *
     * @param oldX x where the path starts
     * @param oldY y where the path starts
     * @param newX x where the path ends
     * @param newY y where the path ends
     * @return the path as calculated by the A Star algorithm
     */
    public final List<Stav> getPath3(Stav start, Stav finish) {
        LinkedHashSet<Stav> openList = new LinkedHashSet<>();
        LinkedHashSet<Stav> closedList = new LinkedHashSet<>();
        openList.add(start); // add starting node to open list
        start.setG(0);
        start.setF(start.getVzdalenostH(finish));
        boolean done = false;
        Stav current;
        while (!openList.isEmpty()) {
            current = lowestFInOpen(openList); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list

            if (current.equals(finish)) { // found goal
                //return calcPath(nodes[oldX][oldY], current);
                return new LinkedList<>();
            }

            // for all adjacent nodes:
            List<Stav> adjacentNodes = current.getNaslednikyList();
            for (Stav currentAdj : adjacentNodes) {
                if (!openList.contains(currentAdj)) { // node is not in openList
                    //currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.setF(currentAdj.getVzdalenostH(finish)); // set h costs of this node (estimated costs to goal)
                    currentAdj.setG(current.getG() + 1); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                } else { // node is in openList
                    for (Stav stav : openList) {
                        if (stav.equals(currentAdj)) {
                            openList.remove(stav);
                            currentAdj.setF(currentAdj.getVzdalenostH(finish)); // set h costs of this node (estimated costs to goal)
                            currentAdj.setG(current.getG() + 1); // set g costs of this node (costs from start to this node)
                            openList.add(currentAdj); // add node to openList
                            break;
                        }
                    }
//                    if (currentAdj.getG()> currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
//                        currentAdj.setPrevious(current); // set current node as previous for this node
//                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
//                    }
                }
            }
        }
        return null; // unreachable
    }

    public List<Stav> getPath4(Stav source, Stav destination) {
        final Map<Stav, Stav> nodesDataMap = new HashMap<>();
        expandovano = 0;
        /**
         * http://stackoverflow.com/questions/20344041/why-does-priority-queue-has-default-initial-capacity-of-11
         */
        openQueue = new PriorityQueue<Stav>(60, new Comparator<Stav>() {
            @Override
            public int compare(Stav o1, Stav o2) {
                if (o1.getF() > o2.getF()) {
                    return 1;
                }
                if (o2.getF() > o1.getF()) {
                    return -1;
                }
                return 0;
            }
        });

        source.setG(0);
        source.setF(source.getVzdalenostH(destination));
        //sourceNodeData.calcF(heuristic.getWeight(source, destination));
        nodesDataMap.put(source, source);
        openQueue.add(source);

        final Map<Stav, Stav> path = new HashMap<>();
        closedList = new HashSet<>();

        while (!openQueue.isEmpty()) {
            final Stav nodeData = openQueue.poll();

            if (nodeData.equals(destination)) {
                //return true;
                //return new ImmutablePair<List<T>, Double>(reconstructPath(path, destination), nodeData.getG());
                return reconstructPath(path, nodeData);
            }

            closedList.add(nodeData);

            for (Stav neighborEntry : nodeData.getNaslednikyList()) {
                expandovano++;
                final Stav entryNode = neighborEntry;
                Stav neighbor = entryNode;
                if (nodesDataMap.containsKey(entryNode)) {
                    neighbor = nodesDataMap.get(entryNode);
                } else {
                    neighbor = neighborEntry;
                    nodesDataMap.put(entryNode, neighbor);
                }

                if (closedList.contains(neighbor)) {
                    continue;
                }

                double distanceBetweenTwoNodes = 1;
                double tentativeG = distanceBetweenTwoNodes + nodeData.getG();

                //if (tentativeG >= neighbor.getG()) {
                neighbor.setG(tentativeG);
                neighbor.setF(neighbor.getVzdalenostH(destination));
                //neighbor.calcF(heuristic.getWeight(neighbor.getNode(), destination));

                path.put(neighbor, nodeData);
                // Java PriorityQueue does not provide correct way to reassign element priority.
                // Force update queue.
                if (openQueue.contains(neighbor)) {

                    openQueue.remove(neighbor);

                }
                openQueue.add(neighbor);
                // }
            }
        }

        return new LinkedList<>();
    }

    public List<Stav> getPath5(Stav source, Stav destination) {

        expandovano = 0;
        /**
         * http://stackoverflow.com/questions/20344041/why-does-priority-queue-has-default-initial-capacity-of-11
         */
        openQueue = new PriorityQueue<Stav>(60, new Comparator<Stav>() {
            @Override
            public int compare(Stav o1, Stav o2) {
                if ((o1.getF() + o1.getG()) > (o2.getF() + o2.getG()))  {
                    return 1;
                }
                if ((o2.getF() + o2.getG()) > (o1.getF() + o1.getG())) {
                    return -1;
                }
                return 0;
            }
        });

        source.setG(0);
        source.setF(source.getVzdalenostH(destination));
        openQueue.add(source);

        final Map<Stav, Stav> path = new HashMap<>();
        closedList = new HashSet<>();

        while (!openQueue.isEmpty()) {
            final Stav actual = openQueue.poll();

            if (actual.equals(destination)) {
                return reconstructPath(path, actual);
            }

            closedList.add(actual);

            for (Stav neighbor : actual.getNaslednikyList()) {
                expandovano++;

                if (closedList.contains(neighbor)) {
                    continue;
                }

                double distanceBetweenTwoNodes = 1;
                double tentativeG = distanceBetweenTwoNodes + actual.getG();

                neighbor.setG(tentativeG);
                neighbor.setF(neighbor.getVzdalenostH(destination));

                path.put(neighbor, actual);

                if (openQueue.contains(neighbor)) {

                    openQueue.remove(neighbor);

                }
                openQueue.add(neighbor);
                // }
            }
        }

        return new LinkedList<>();
    }

    private List<Stav> reconstructPath(Map<Stav, Stav> path, Stav destination) {
        assert path != null;
        assert destination != null;

        final List<Stav> pathList = new ArrayList<>();
        pathList.add(destination);
        while (path.containsKey(destination)) {
            destination = path.get(destination);
            pathList.add(destination);
        }
        Collections.reverse(pathList);
        return pathList;
    }

    public int getExpandovano() {
        return 0;//expandovano;
    }

    public int getMax() {
        return 0;//max;
    }

    public static void main(String[] args) throws IOException {
//        try (ObjectInputStream oos
//                = new ObjectInputStream(new FileInputStream("p.ser"))) {
//
//            //Object r1 = oos.readObject();
//            //System.out.println("Done");
//
//        } 

        ArrayList<Stav> start = new ArrayList<Stav>();
        Stav s1 = new Stav();
        s1.setData("724506831");
        s1.setPoziceDiry(4);
        start.add(s1);
        Stav s2 = new Stav();
        s2.setData("806547231");
        s2.setPoziceDiry(1);
        start.add(s2);
        Stav s3 = new Stav();
        s3.setData("123804765");
        s3.setPoziceDiry(4);
        start.add(s3);

        Stav c = new Stav();
        c.setData("012345678");
        c.setPoziceDiry(0);

        for (Stav st : start) {
            Stavy_listem_AStar s = new Stavy_listem_AStar();
            System.out.println("Hledání cesty algoritmem A* ze stavu " + st);
            List<Stav> p1 = s.getPath5(st, c);
            System.out.println("Řešení po " + p1.size() + " krocích při " + s.openQueue.size() + " stavech ve frontě a " + s.closedList.size() + " v closed za expandovani " + " stavů:");
            for (Stav stav : p1) {
                System.out.println(stav);
            }
            System.out.println("*********************");

        }

    }

    private Stav lowestFInOpen(LinkedHashSet<Stav> openList) {
        Stav min = openList.stream().findFirst().get();
        for (Stav stav : openList) {
            if (min.getF() > stav.getF()) {
                min = stav;
            }
        }
        return min;
    }
}
