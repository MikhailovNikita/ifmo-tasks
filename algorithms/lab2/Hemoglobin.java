import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by mikha on 15.11.2015.
 */
public class Hemoglobin {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("Hemoglobin.in");
        File outputFile = new File("Hemoglobin.out");
        PrintWriter out = new PrintWriter(outputFile);
        BufferedReader br = new BufferedReader(new FileReader(inputFile)) ;
        String v = br.readLine();
        int n = Integer.parseInt(v);

        char op;
        int x, k;
        long[] hemo = new long[n + 10];
        int hemoHead = 0;

        for (int i = 0; i < n; i++) {
            String s = br.readLine();
            op = s.charAt(0);

            if (op == '+') {
                x = Integer.parseInt(s.substring(1));
                if (hemoHead > 0) {
                    hemo[hemoHead] = hemo[hemoHead - 1] + x;
                    hemoHead++;
                }else{
                    hemo[0]=x;
                    hemoHead++;
                }
            }

            if (op == '-') {
                hemoHead--;
                if(hemoHead>0){
                    out.println(hemo[hemoHead]-hemo[hemoHead-1]);
                }else{
                    out.println(hemo[hemoHead]);
                }

            }

            if (op == '?') {
                k = Integer.parseInt(s.substring(1));
                if(hemoHead-1-k>=0){
                    out.println(hemo[hemoHead - 1] - hemo[hemoHead - 1 - k]);
                }else{
                    out.println(hemo[hemoHead - 1]);
                }

            }
        }
        out.close();
    }
}
