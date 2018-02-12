import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 02.01.2016.
 */
public class AddEdge {

    static class Pair {
        int number;
        int parity;

        Pair(int a, int b) {
            this.number = a;
            this.parity = b;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader("addedge.in"));
        PrintWriter out = new PrintWriter(new File("addedge.out"));
        String[] s = in.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        Pair[] parent = new Pair[n + 1];
        boolean[] biGraph = new boolean[n + 1];
        int[] rank = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            makeSet(parent, rank, biGraph, i);
        }
        boolean isBroken = false;
        for (int i = 0; i < m; i++) {
            String[] cmd = in.readLine().split(" ");
            addEdge(parent, rank, biGraph, Integer.parseInt(cmd[0]), Integer.parseInt(cmd[1]));
            if (isBiGraph(biGraph, parent, Integer.parseInt(cmd[0]))) {
                out.print("1");
            } else {
                out.print("0");
            }
        }
        out.close();
    }


    static void makeSet(Pair[] parent, int[] rank, boolean[] biGraph, int v) {
        parent[v] = new Pair(v, 0);
        rank[v] = 0;
        biGraph[v] = true;
    }

    static Pair findSet(Pair[] parent, int v) {
        if (v != parent[v].number) {
            int parity = parent[v].parity;
            parent[v] = findSet(parent, parent[v].number);
            parent[v].parity ^= parity;
        }
        return parent[v];
    }

    static void addEdge(Pair[] parent, int[] rank, boolean[] biGraph, int a, int b) {
        Pair p = findSet(parent, a);
        a = p.number;
        int x = p.parity;

        Pair pb = findSet(parent, b);
        b = pb.number;
        int y = pb.parity;

        if (a == b) {
            if (x == y)
                biGraph[a] = false;
        } else {
            if (rank[a] < rank[b]) {
                int swap = a;
                a = b;
                b = swap;
            }
            parent[b] = new Pair(a, x ^ y ^ 1);
            biGraph[a] &= biGraph[b];
            if (rank[a] == rank[b])
                ++rank[a];
        }
    }

    static boolean isBiGraph(boolean[] biGraph, Pair[] parent, int v) {
        return biGraph[findSet(parent, v).number];
    }
}
