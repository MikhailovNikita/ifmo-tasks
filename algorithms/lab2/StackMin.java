
import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by mikha on 15.11.2015.
 */

public class StackMin {
    static class Scanner {
        BufferedReader br;
        StringTokenizer t;
        Scanner(File file) throws Exception {
            br = new BufferedReader(new FileReader(file));
            t = new StringTokenizer("");
        }

        boolean hasNext() throws Exception {
            while (!t.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null)
                    return false;
                t = new StringTokenizer(line);
            }
            return true;
        }

        String next() throws Exception {
            if (hasNext()) {
                return t.nextToken();
            }
            return null;
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }

        void close() throws Exception {
            br.close();
        }
    }


    public static void main(String[] args) throws Exception {
        File inputFile = new File("stack-min.in");
        File outputFile = new File("stack-min.out");
        PrintWriter out = new PrintWriter(outputFile);
        Scanner in = new Scanner(inputFile);
        int minimum = Integer.MAX_VALUE;
        int N = in.nextInt();
        int[] sup = new int[N + 10];
        int supPeek = 0;
        int[] min = new int[N + 10];
        int minPeek = 0;
        int local;
        int op;

        for (int i = 0; i < N; i++) {
            op = in.nextInt();

            if (op == 1) {
                local = in.nextInt();
                if (local <= minimum) {
                    min[minPeek++] = local;
                    minimum = local;
                }
                sup[supPeek++] = local;
            } else {
                if (op == 2) {
                    if (sup[supPeek - 1] == min[minPeek - 1]) {
                        minPeek--;
                        if (minPeek > 0) {
                            minimum = min[minPeek - 1];
                        } else {
                            minimum = Integer.MAX_VALUE;
                        }

                    }
                    supPeek--;

                } else {
                    out.println(minimum);
                }
            }

        }


        out.close();

    }
}