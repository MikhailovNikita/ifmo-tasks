import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by mikha on 14.11.2015.
 */
public class Postfix {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("Postfix.in");
        File outputFile = new File("Postfix.out");
        PrintWriter out = new PrintWriter(outputFile);
        Scanner file = new Scanner(inputFile);
        StringTokenizer t = new StringTokenizer(file.nextLine());
        String smth;
        int var;
        int[] numbers = new int[300];
        int head = 0;

        while (t.hasMoreTokens()) {
            smth = t.nextToken();
            try {
                var = Integer.parseInt(smth);
                numbers[head] = var;
                head++;
            } catch (Exception e) {
                if (smth.equals("-")) {
                    numbers[head-2]=numbers[head-2]-numbers[head-1];
                    head--;
                }
                if (smth.equals("+")) {
                    numbers[head-2]=numbers[head-2]+numbers[head-1];
                    head--;
                }
                if (smth.equals("*")) {
                    numbers[head-2]=numbers[head-2]*numbers[head-1];
                    head--;
                }

            }
        }

        out.print(numbers[0]);
        out.close();
    }
}
