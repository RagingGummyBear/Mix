package com.grizzlypenguins.dungeondart.characters;

/**
 * Created by Darko on 01.12.2015.
 */
import com.grizzlypenguins.dungeondart.MyPoint;
import com.grizzlypenguins.dungeondart.myFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;


class Edge implements Comparable<Edge> {

    double v1;
    double v2;
    double weight;

    Edge(double v1, double v2, double weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    double getFrom() {
        return v1;
    }

    double getTo() {
        return v2;
    }

    @Override
    public int compareTo(Edge o) {
        // po rastechki redosled
        if (weight < o.weight) {
            return -1;
        }
        if (weight > o.weight) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Edge:" + "v1: " + v1 + ", v2: " + v2 + ", weight:" + weight;
    }
}


class Graph {

    static double INFINITY = 1000000000;
    private double brJazli; // broj na jazli
    private double brRebra; // broj na rebra
    ArrayList<Edge> neighbours[]; // lista na site sosedi na site jazli
    HashMap<String, Edge> hm; // za da pristapime kon vrska vo konstantno vreme

    String encode(double i, double j) {
        return Double.toString(i) + " " + Double.toString(j);
    }

    double getBrJazli() {
        return brJazli;
    }

    double getE() {
        return brRebra;
    }

    Graph(int brJazli) {
        this.brJazli = brJazli;
        neighbours = new ArrayList[brJazli];
        for (int i = 0; i < brJazli; i++) {
            neighbours[i] = new ArrayList<Edge>(); // sosedi na i-tiot jazol
        }
        brRebra = 0;
        hm = new HashMap<String, Edge>();
    }

    void addEdge(double v1, double v2, double weight) {
        Edge e = new Edge(v1, v2, weight);
        addEdge(e);
    }

    void addEdge(Edge e) {
        neighbours[(int)e.v1].add(e);
        hm.put(encode(e.v1, e.v2), e);
        brRebra++;
    }

    void deleteEdge(int v1, int v2) {
        Edge e = hm.get(encode(v1, v2));
        if (e != null) {
            deleteEdge(e);
        }
    }

    void deleteEdge(Edge e) {
        neighbours[(int)e.v1].remove(e);
        hm.remove(encode(e.v1, e.v2));
        brRebra--;
    }
    Edge getEdge(int v1, int v2) {
        return hm.get(encode(v1, v2));
    }
    public Edge[] getAllEdges() {
        int i, j = 0;

        int totalEdges = 0;
        for (i = 0; i < brJazli; i++) {
            totalEdges += neighbours[i].size();
        }
        totalEdges /= 2;

        Edge edges[] = new Edge[totalEdges];

        for (i = 0; i < brJazli; i++) {

            Iterator<Edge> it = neighbours[i].iterator();
            while (it.hasNext()) {
                Edge current = it.next();
                if (current.v2 > current.v1) {
                    edges[j] = new Edge(current.v1, current.v2, current.weight);
                    j++;
                }
            }

        }

        return edges;
    }

    Jazol dijkstraArray[];

    // dijkstra
    void dijkstraOdEdnoTeme(int source) {
        int i;

        dijkstraArray = new Jazol[(int)brJazli];

        for (i = 0; i < brJazli; i++) {
            dijkstraArray[i] = new Jazol(i);
        }
        dijkstraArray[source].minDistance = 0;// minimalno rastojanie na pocetok e 0;

        PriorityQueue<Jazol> q = new PriorityQueue<Jazol>();
        q.add(dijkstraArray[source]);

        while (!q.isEmpty()) {
            Jazol momJazol = q.poll();

            for (Edge e : neighbours[(int)momJazol.index]) {
                Jazol v = dijkstraArray[(int)e.v2]; //daj mi go sosedot
                double weight = e.weight;
                double newDistance = momJazol.minDistance + weight;

                if (newDistance < v.minDistance) {
                    q.remove(v);
                    v.minDistance = newDistance;
                    v.previous = momJazol;
                    q.add(v);
                }
            }
        }

    }

    ArrayList<Jazol> getShortestPathTo(int target) {

        ArrayList<Jazol> path = new ArrayList<Jazol>();

        for (Jazol vertex = dijkstraArray[target]; vertex != null; vertex = vertex.previous) {
            path.add(vertex);
        }

        Collections.reverse(path);
        return path;
    }

    public ArrayList<Double> getPateka(int target) {

        ArrayList<Double> path = new ArrayList<Double>();

        for (Jazol vertex = dijkstraArray[target]; vertex != null; vertex = vertex.previous) {
            path.add(vertex.index);
        }

        Collections.reverse(path);
        return path;
    }
}

class Jazol implements Comparable<Jazol> {

    double index;
    double minDistance;
    Jazol previous;

    Jazol(double index) {
        this.index = index;
        minDistance = Graph.INFINITY;
        previous = null;
    }

    @Override
    public int compareTo(Jazol o) {
        // rastecki redosled
        return Double.compare(this.minDistance, o.minDistance);
    }

    @Override
    public String toString() {
        return "Jazol: " + "index: " + index + ", minDistance: " + minDistance + ", previous: " + previous;
    }
}
public class Algoritmi {

    public static double brJazli, brRebra, start, end, voda;

    public ArrayList<Jazol> returnPath(int niza[][],MyPoint monster, MyPoint heroj) {
        ArrayList<String> jazli = new ArrayList<String>();
        int temp = Integer.parseInt(niza.length+""+niza[0].length);
        Graph g = new Graph(temp);
        for (int i = 0; i < niza.length; i++) {
            for (int j = 0; j < niza[0].length; j++) {
                ArrayList sosedi = getSosedi(i, j, niza);
                for (int k = 0; k < sosedi.size(); k++) {
                    int from = i * 10 + j;
                    int to = Integer.parseInt(sosedi.get(k) + "");
                    g.addEdge(from, to, 1);
                    g.addEdge(to, from, 1);
                }
            }
        }
        int from = (int)monster.x * myFactory.TILENUMBER + (int)monster.y;
        int to = (int)heroj.x * myFactory.TILENUMBER + (int)heroj.y;
        g.dijkstraOdEdnoTeme(from);
        ArrayList<Jazol> d = g.getShortestPathTo(to);
        /*for(int i = 0; i < d.size(); i++){
            System.out.println(d.get(i).index+" "+d.get(i).minDistance);
        }*/
        return d;
    }

    public static ArrayList<String> getSosedi(int x, int y, int niza[][]){
        ArrayList<String> nizi = new ArrayList<>();
        int k = x; int j = y;
        if(x+1 < niza.length && y < niza.length ){
            if(niza[x+1][y] >= 1){
                k+=1;
                nizi.add(k+""+j);
            }
        }
        if(x < niza.length && y+1 < niza.length ){
            if(niza[x][y+1] >= 1){
                j+=1;
                nizi.add(x+""+j);
            }
        }
        return nizi;
    }
}