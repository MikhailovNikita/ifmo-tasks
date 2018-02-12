import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 02.01.2016.
 */
public class Cutting {


    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        PrintWriter out = new PrintWriter(new File("output.txt"));
        String[] s = in.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        int k = Integer.parseInt(s[2]);
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

    }



    void makeSet(int[] parent, int[] rank, int v) {
        parent[v] = v;
        rank[v] = 0;
    }

    static int findSet(int[] parent, int[] rank, int v) {
        if (v == parent[v])
            return v;
        return parent[v] = findSet(parent, rank, parent[v]);
    }

    static void unionSets(int[] parent, int[] rank, int a, int b) {
        a = findSet(parent, rank, a);
        b = findSet(parent, rank, b);
        if (a != b) {
            if (rank[a] < rank[b]) {
                int swap = a;
                a = b;
                b = swap;
            }
            parent[b] = a;
            if (rank[a] == rank[b]) {
                ++rank[a];
            }
        }
    }

}

