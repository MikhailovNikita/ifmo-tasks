

import java.*.*;

/**
 * Created by allah on 17.12.2015.
 */
public class RSQ {
    static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }


    static class SegTree {
        protected long[] tree;
        int size;

        SegTree(int n) {
            this.size = 4 * n + 1;
            this.tree = new long[size];
        }

        void buildTree(int lb, int rb, int v, long[] arr) {
            if (lb == rb)
                this.tree[v] = arr[lb];
            else {
                int m = (lb + rb) / 2;
                buildTree(lb, m, v * 2, arr);
                buildTree(m + 1, rb, v * 2 + 1, arr);
                this.tree[v] = this.tree[v * 2] + this.tree[v * 2 + 1];
            }
        }

        long getSum(int v, int lb, int rb, int l, int r) {
            //System.out.println(v + " " + lb + " " + rb + " " + l + " " + r);
            if (l > r)
                return 0;
            if (l == lb && r == rb)
                return this.tree[v];
            int m = (lb + rb) / 2;
            return getSum(v * 2, lb, m, l, min(r, m)) + getSum(v * 2 + 1, m + 1, rb, max(l, m + 1), r);
        }

        void update(int v, int lb, int rb, long pos, long new_val) {
            if (lb == rb)
                this.tree[v] = new_val;
            else {
                int m = (lb + rb) / 2;
                if (pos <= m)
                    update(v * 2, lb, m, pos, new_val);
                else
                    update(v * 2 + 1, m + 1, rb, pos, new_val);
                this.tree[v] = this.tree[v * 2] + this.tree[v * 2 + 1];
            }
        }

        void print() {
            for (int i = 0; i < this.size; i++) {
                System.out.println(this.tree[i]);
            }

        }

    }

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new File("rsq.out"));
        BufferedReader br = new BufferedReader(new FileReader(new File("rsq.in")));
        int n = Integer.parseInt(br.readLine());
        SegTree tree = new SegTree(n);
        long[] a = new long[n];
        String s = br.readLine();
        String[] num = s.split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(num[i]);
        }
        tree.buildTree(0, n - 1, 1, a);
        while ((s = br.readLine()) != null) {
            String[] op = s.split(" ");
            if (op[0].equals("sum")) {
                out.println(tree.getSum(1, 0, n - 1, Integer.parseInt(op[1]) - 1, Integer.parseInt(op[2]) - 1));
            } else {
                tree.update(1, 0, n - 1, Long.parseLong(op[1]) - 1, Long.parseLong(op[2]));
            }
        }
        out.close();
    }
}
