import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Created by mikha on 02.01.2016.
 */
public class Candies {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("candies.in"));
        PrintWriter out = new PrintWriter(new File("candies.out"));
        int n = Integer.parseInt(in.readLine());
        String[] cand = in.readLine().split(" ");
        int[] candies = new int[n];
        int segSize = (int) Math.round(Math.sqrt(n));
        int[] segValue = new int[n / segSize + 1];
        for (int i = 0; i < n; i++) {
            candies[i] = Integer.parseInt(cand[i]);
        }
        Arrays.sort(candies);

        for (int i = 0; i < n; i += segSize) {
            segValue[i / segSize] = 0;
        }

        int m = Integer.parseInt(in.readLine());
        String[] greed = in.readLine().split(" ");

        for (int i = 0; i < m; i++) {
            int gr = Integer.parseInt(greed[i]);
            int counter = 0;
            int place = findSegment(candies, segValue, gr, segSize);

            if (place == -1) {
                System.out.println("DA TY OHUEL");
                out.println(counter);
            } else {
                if (candies[place] - segValue[place / segSize] < gr) {
                    place++;
                    while ((place % segSize != 0) && (place < n)) {
                        if (candies[place] - segValue[place / segSize] >= gr) {
                            counter++;
                            candies[place]--;
                        }
                        place++;
                    }

                }
                counter += n - place;
                if (place != n) {
                    for (int j = place / segSize; j < segValue.length; j++) {
                        segValue[j]++;
                    }
                }
                out.println(counter);
            }

        }
        out.close();
    }

    static int findSegment(int[] candies, int[] segValue, int greed, int segSize) {
        int i = 0;
        int place = -1;
        boolean flag = false;
        while ((i < candies.length) && (!flag)) {
            if (candies[i] - segValue[i / segSize] < greed) {
                i += segSize;
            } else {
                if (i - segSize >= 0) {
                    place = i - segSize;
                } else {
                    place = i;
                }
                flag = true;
            }
        }
        if(!flag){
            return i-segSize;
        }
        return place;
    }
}
