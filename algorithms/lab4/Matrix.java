import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by mikha on 01.03.2016.
 */
public class Matrix {
    public static void writeAnswer(int[][] answer, int i, int j, PrintWriter out) {

        if (i == j) {
            out.write("A");
        } else {
            out.write("(");
            writeAnswer(answer, i, answer[i][j], out);
            writeAnswer(answer, answer[i][j] + 1, j, out);
            out.write(")");
        }
    }

    public static void matrixMultiplication(int[] p, PrintWriter out) {
        int n = p.length - 1;
        int[][] d = new int[n + 1][n + 1];
        int[][] a = new int[n + 1][n + 1];


        for (int i = 1; i <= n; i++) {
            d[i][i] = 0;
        }

        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                d[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int q = d[i][k] + d[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < d[i][j]) {
                        d[i][j] = q;
                        a[i][j] = k;
                    }
                }
            }
        }
        writeAnswer(a, 1, n, out);
    }


    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("matrix.in"));
        PrintWriter out = new PrintWriter(new File("matrix.out"));

        int n = sc.nextInt();
        int[] matrix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            matrix[i] = sc.nextInt();
            if (i == n - 1) {
                matrix[n] = sc.nextInt();
            } else {
                sc.nextInt();
            }
        }

        //System.out.println(Arrays.deepToString(matrix));
        matrixMultiplication(matrix, out);

        out.close();

    }
}
