import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by mikha on 17.12.2015.
 */
public class RMQ {
    static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }


    static class SegTree {
        protected int[] tree;
        int size;

        SegTree(int n) {
            this.size = 4 * n;
            this.tree = new int[4 * n];
        }

        void buildTree(int lb, int rb, int v, int[] arr) {
            if (lb == rb)
                this.tree[v] = arr[lb];
            else {
                int m = (lb + rb) / 2;
                buildTree(lb, m, v * 2, arr);
                buildTree(m + 1, rb, v * 2 + 1, arr);
                this.tree[v] = min(this.tree[v * 2], this.tree[v * 2 + 1]);
            }
        }

        int getMin(int v, int lb, int rb, int l, int r) {
            //System.out.println(v + " " + lb + " " + rb + " " + l + " " + r);
            if (l > r)
                return Integer.MAX_VALUE;
            if (l == lb && r == rb)
                return this.tree[v];
            int m = (lb + rb) / 2;
            return min(getMin(v * 2, lb, m, l, min(r, m)), getMin(v * 2 + 1, m + 1, rb, max(l, m + 1), r));
        }

        void update(int v, int lb, int rb, int pos, int new_val) {
            if (lb == rb)
                this.tree[v] = new_val;
            else {
                int m = (lb + rb) / 2;
                if (pos <= m)
                    update(v * 2, lb, m, pos, new_val);
                else
                    update(v * 2 + 1, m + 1, rb, pos, new_val);
                this.tree[v] = min(this.tree[v * 2], this.tree[v * 2 + 1]);
            }
        }

        void print() {
            for (int i = 0; i < this.size; i++) {
                System.out.println(this.tree[i]);
            }

        }

    }

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new File("rmq.out"));
        BufferedReader br = new BufferedReader(new FileReader(new File("rmq.in")));
        int n = Integer.parseInt(br.readLine());
        SegTree tree = new SegTree(n);
        int[] a = new int[n];
        String s = br.readLine();
        String[] num = s.split(" ");

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(num[i]);
        }
        tree.buildTree(0, n - 1, 1, a);
        tree.print();
        while ((s=br.readLine())!=null) {
            String[] op = s.split(" ");
            if (op[0].equals("min")) {
                out.println(tree.getMin(1, 0, n - 1, Integer.parseInt(op[1]) - 1, Integer.parseInt(op[2]) - 1));
            } else {
                tree.update(1, 0, n - 1, Integer.parseInt(op[1]) - 1, Integer.parseInt(op[2]));
            }
        }
        out.close();

    }
}
