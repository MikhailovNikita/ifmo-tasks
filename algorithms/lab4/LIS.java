import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 27.02.2016.
 */
public class LIS {
    static int binarySearch(int[] d, int x) {
        int l = 0, r = d.length-1;
        int mid;
        while (l < r - 1) {
            mid = (l+r) / 2;
            if (d[mid] > x) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return r;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("lis.in"));
        PrintWriter out = new PrintWriter(new File("lis.out"));
        int n = sc.nextInt();
        int[] a = new int[n];
        int[] d = new int[n + 1];
        int[] prev = new int[n];
        int[] pos = new int[n+1];
        int length = 0;

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        for (int i = 0; i < n; i++) {
            prev[i] = -1;
            pos[i] = -1;
        }

        d[0] = Integer.MIN_VALUE;

        for (int i = 1; i < n+1; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            int ind = binarySearch(d, a[i]);
            if ((d[ind - 1] < a[i]) && (a[i] < d[ind])) {
                d[ind] = a[i];
                pos[ind] = i;
                prev[i] = pos[ind - 1];
                maxLength = maxLength > ind ? maxLength : ind;
            }

        }

        int[] ans = new int[maxLength];
        int i = pos[maxLength];
        out.println(maxLength);

        while (i > -1) {
            ans[--maxLength] = a[i];
            i = prev[i];
        }

        for (int j = 0; j < ans.length; j++) {
            out.print(ans[j]+" ");
        }
        out.close();
    }
}
