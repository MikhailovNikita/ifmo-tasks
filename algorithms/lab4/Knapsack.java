import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 28.02.2016.
 */
public class Knapsack {
    static int counter = 0;

    public static void getAnswer(int[][] d, int i, int j, int[] ans ,int[] weight) { //why there's so many??:(
        if (d[i][j] == 0) {
            return;
        }
        if (d[i][j] == d[i - 1][j]) {
            getAnswer(d, i-1, j, ans ,weight);
        } else {
            getAnswer(d, i-1, j-weight[i-1]>-1?j-weight[i-1]:0, ans,weight); // >_< great synt
            ans[counter++] = i;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("knapsack.in"));
        PrintWriter out = new PrintWriter(new File("knapsack.out"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] value = new int[n];
        int[] weight = new int[n];
        int[][] d = new int[n + 1][m + 1]; // i "like" dynamic programming
        for (int i = 0; i < n; i++) {
            weight[i] = sc.nextInt();
        }
        for (int i = 0; i < n; i++) {
            value[i] = sc.nextInt();
        }

        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[i].length; j++) {
                if ((i == 0) || (j == 0)) {
                    d[i][j] = 0;
                    continue;
                }
                if (j - weight[i - 1] < 0) {
                    d[i][j] = d[i - 1][j];
                } else {
                    d[i][j] = Math.max(d[i - 1][j], d[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
            }
        }
        int[] ans = new int[n];
        getAnswer(d, n, m, ans, weight);
        out.println(counter);
        for(int i = 0 ;i<counter;i++){
            out.print(ans[i]+" ");
        }
        out.close();
    }
}
