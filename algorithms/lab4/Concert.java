import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 28.02.2016.
 */
public class Concert {
    public static boolean[][] fillArray(int m,int[] deltas,int last,boolean[][] d,int i){

        if(i<d.length){
            int lastInc = last+deltas[i-1];
            int lastDec = last-deltas[i-1];
            if ((!(lastInc>m)) && (!(d[i][lastInc]))){
                d[i][lastInc] = true;
                fillArray(m,deltas,lastInc,d,i+1);
            }
            if((!(lastDec<0)) && (!(d[i][lastDec]))){
                d[i][lastDec] = true;
                fillArray(m,deltas,lastDec,d,i+1);
            }
        }
        return d;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("concert.in"));
        PrintWriter out = new PrintWriter(new File("concert.out"));
        int n = in.nextInt();
        int[] deltas = new int[n];
        for (int i = 0; i < n; i++) {
            deltas[i] = in.nextInt();
        }
        int b = in.nextInt();
        int m = in.nextInt();
        boolean[][] d = new boolean[n+1][m+1];
        d = fillArray(m,deltas,b,d,1);
        int i = m;
        while((i>-1) && (!(d[n][i]))){
            i--;
        }
        out.print(i);
        out.close();
    }
}
