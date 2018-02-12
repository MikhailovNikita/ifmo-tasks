import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by mikha on 04.03.2016.
 */
public class Monsters {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("monsters.in"));
        PrintWriter out = new PrintWriter(new File("monsters.out"));
        int n = sc.nextInt();
        long[] dread = new long[n];
        boolean[] price = new boolean[n]; //if price[i]==true then monster #i costs 2 coins

        for (int i = 0; i < n; i++) {
            dread[i] = sc.nextLong();
        }
        for (int i = 0; i < n; i++) {
            price[i] = sc.nextInt() > 1;
        }

        long[][] d = new long[n + 1][2 * n + 3]; //use 2n+3 because of overflowing when trying to fill d[i+1][j+costOfMonster]

        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < 2 * n + 3; j++) {
                d[i][j] = Long.MIN_VALUE;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * n; j++) {
                d[i + 1][j + (price[i] ? 2 : 1)] = Math.max((d[i][j] + dread[i]), d[i + 1][j + (price[i] ? 2 : 1)]);
                if (!(d[i][j] < dread[i])) {
                    d[i + 1][j] = Math.max(d[i][j], d[i + 1][j]);
                }
            }
        }

        for (int i = 0; i <= 2 * n; i++) {
            if (d[n][i] > 0) {
                out.print(i);
                break;
            }
        }

        out.close();

    }
}
