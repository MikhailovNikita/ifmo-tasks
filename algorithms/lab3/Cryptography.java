import java.io.*;

/**
 * Created by mikha on 18.12.2015.
 */
public class Cryptography {
    static int mod;

    static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    static class Matrix {
        static Matrix e = new Matrix(1, 0, 0, 1);
        int c11, c12, c21, c22;

        Matrix(int a, int b, int c, int d) {
            c11 = a;
            c12 = b;
            c21 = c;
            c22 = d;
        }

        static Matrix matrixMultiplication(Matrix first, Matrix second) {

            int a = (first.c11 * second.c11 + first.c12 * second.c21) % mod;
            int b = (first.c11 * second.c12 + first.c12 * second.c22) % mod;
            int c = (first.c21 * second.c11 + first.c22 * second.c21) % mod;
            int d = (first.c21 * second.c12 + first.c22 * second.c22) % mod;
            return new Matrix(a, b, c, d);
        }
    }

    static class SegTree {
        protected Matrix[] tree;
        int size;

        SegTree(int n) {
            this.size = 4 * n;
            this.tree = new Matrix[size];
        }

        void buildTree(int lb, int rb, int v, Matrix[] arr) {
            if (lb == rb)
                this.tree[v] = arr[lb];
            else {
                int m = (lb + rb) / 2;
                buildTree(lb, m, v * 2, arr);
                buildTree(m + 1, rb, v * 2 + 1, arr);
                this.tree[v] = Matrix.matrixMultiplication(this.tree[v * 2], this.tree[v * 2 + 1]);
            }
        }

        Matrix getMatrix(int v, int lb, int rb, int l, int r) {
            //System.out.println(v + " " + lb + " " + rb + " " + l + " " + r);
            if (l > r)
                return Matrix.e;
            if (l == lb && r == rb)
                return this.tree[v];
            int m = (lb + rb) / 2;
            return Matrix.matrixMultiplication(getMatrix(v * 2, lb, m, l, min(r, m)), getMatrix(v * 2 + 1, m + 1, rb, max(l, m + 1), r));
        }


    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("crypto.in")));
        PrintWriter out = new PrintWriter(new File("crypto.out"));
        String[] op = br.readLine().split(" ");
        int n = Integer.parseInt(op[1]);
        SegTree tree = new SegTree(n);
        int m = Integer.parseInt(op[2]);
        int r = Integer.parseInt(op[0]);
        Cryptography.mod = r;
        Matrix[] arr = new Matrix[n];
        for (int i = 0; i < n; i++) {
            String[] mat1 = br.readLine().split(" ");
            String[] mat2 = br.readLine().split(" ");
            br.readLine();
            arr[i] = new Matrix(Integer.parseInt(mat1[0]), Integer.parseInt(mat1[1]), Integer.parseInt(mat2[0]), Integer.parseInt(mat2[1]));
        }
        tree.buildTree(0, n - 1, 1, arr);
        for (int i = 0; i < m; i++) {
            op = br.readLine().split(" ");
            Matrix ans = tree.getMatrix(1, 0, n - 1, Integer.parseInt(op[0]) - 1, Integer.parseInt(op[1]) - 1);
            out.println(ans.c11 + " " + ans.c12);
            out.println(ans.c21 + " " + ans.c22);
            out.println();
        }
        out.close();

    }


}
