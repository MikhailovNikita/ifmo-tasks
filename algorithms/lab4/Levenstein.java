
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 28.02.2016.
 */
public class Levenstein {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("levenshtein.in")));
        PrintWriter out = new PrintWriter(new File("levenshtein.out"));
        String s1 = in.readLine();
        String s2 = in.readLine();
        if(s2==null){
            s2="";
        }
        if(s1==null){
            s1="";
        }
        char[] first = new char[s1.length() + 1];
        char[] second = new char[s2.length() + 1];
        int[][] d = new int[first.length][second.length];
        for (int i = 1; i < first.length; i++) {
            first[i] = s1.charAt(i - 1);
        }
        for (int i = 1; i < second.length; i++) {
            second[i] = s2.charAt(i - 1);
        }

        for (int i = 0; i < first.length; i++) {
            for (int j = 0; j < second.length; j++) {
                if ((i == 0) && (j == 0)) {
                    d[i][j] = 0; //empty string turns into empty for free
                    continue;
                }
                if ((i == 0) && (j > 0)) {
                    d[i][j] = j; // just j insertions
                    continue;
                }
                if ((i > 0) && (j == 0)) {
                    d[i][j] = i; //just i insertions
                    continue;
                }

                if (first[i] == second[j]) {
                    d[i][j] = d[i - 1][j - 1]; //do nothing
                    continue;
                }

                if (first[i] != second[j]) {
                    d[i][j] = Math.min(Math.min(d[i][j - 1], d[i - 1][j])+1, Math.min(d[i - 1][j], d[i - 1][j - 1])+1);
                    continue; // choose the best option from delete,insert and replace
                }
            }
        }

        out.print(d[first.length - 1][second.length - 1]);
        out.close();
    }

}