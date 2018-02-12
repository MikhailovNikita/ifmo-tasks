import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 27.02.2016.
 */
public class LCS {
    static class Pair {
        int i;
        int j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void printLCS(int i, int j, Pair[][] p, int[] s1, PrintWriter out) {
        if ((i == 0) || (j == 0)) {
            return;
        }
        if ((p[i][j].i == i - 1) && (p[i][j].j == j - 1)) {
            printLCS(i - 1, j - 1, p, s1, out);
            out.print(s1[i] + " ");
        } else {
            if ((p[i][j].i == i - 1) && (p[i][j].j == j)) {
                printLCS(i - 1, j, p, s1, out);
            } else {
                printLCS(i, j - 1, p, s1, out);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("lcs.in"));
        PrintWriter out = new PrintWriter(new File("lcs.out"));
        int n = in.nextInt() + 1;
        int[] s1 = new int[n];
        for (int i = 1; i < n; i++) {
            s1[i] = in.nextInt();
        }

        int m = in.nextInt() + 1;
        int[] s2 = new int[m];
        for (int i = 1; i < m; i++) {
            s2[i] = in.nextInt();
        }
        Pair[][] p = new Pair[n][m];
        int[][] d = new int[n][m];
        int maxLength = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if ((j == 0) || (i == 0)) {
                    d[i][j] = 0;
                } else {
                    if (s1[i] == s2[j]) {
                        d[i][j] = d[i - 1][j - 1] + 1;
                        p[i][j] = new Pair(i - 1, j - 1);
                        if (d[i][j] > maxLength) {
                            maxLength = d[i][j];
                        }
                    } else {
                        if (d[i - 1][j] < d[i][j - 1]) {
                            d[i][j] = d[i][j - 1];
                            p[i][j] = new Pair(i, j - 1);

                        } else {
                            d[i][j] = d[i - 1][j];
                            p[i][j] = new Pair(i - 1, j);
                        }
                    }
                }
            }
        }

        out.println(maxLength);
        int[] ans = new int[maxLength];
        printLCS(n - 1, m - 1, p, s1, out);
        out.close();
    }
}
