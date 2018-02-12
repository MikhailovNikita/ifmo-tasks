import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by mikha on 15.11.2015.
 */
public class Formation {
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

        String nextLine() throws Exception {
            return next();
        }

        void close() throws Exception {
            br.close();
        }
    }

    static class Soldier {
        int number;
        int left;
        int right;
        static Soldier[] soldierArray = new Soldier[75010];

        public Soldier(int number, int left, int right) {
            this.number = number;
            this.left = left;
            this.right = right;
        }

        static void putLeft(int i, int j) {
            if (soldierArray[j].left != 0) {
                soldierArray[i].left = soldierArray[j].left;
                soldierArray[soldierArray[j].left].right = i;
            }
            soldierArray[i].right = j;
            soldierArray[j].left = i;
        }

        static void putRight(int i, int j) {
            if (soldierArray[j].right != 0) {
                soldierArray[i].right = soldierArray[j].right;
                soldierArray[soldierArray[j].right].left = i;
            }
            soldierArray[i].left = j;
            soldierArray[j].right = i;
        }

        static void leave(int i) {
            soldierArray[soldierArray[i].left].right=soldierArray[i].right;
            soldierArray[soldierArray[i].right].left=soldierArray[i].left;
            soldierArray[i].left=0;
            soldierArray[i].right=0;
        }

    }

    public static void main(String[] args) throws Exception {
        File inputFile = new File("formation.in");
        File outputFile = new File("formation.out");
        Scanner in = new Scanner(inputFile);
        PrintWriter out = new PrintWriter(outputFile);
        int n, m;
        n = in.nextInt();
        m = in.nextInt();

        Soldier[] soldierArray = new Soldier[n + 10];

        for (int k = 0; k < n + 10; k++) {
            Soldier newSoldier = new Soldier(k, 0, 0);
            Soldier.soldierArray[k]=newSoldier;
        }
        String order;
        int i, j;

        for (int k = 0; k < m; k++) {
            order = in.nextLine();

            if (order.equals("left")) {
                i = in.nextInt();
                j = in.nextInt();
                Soldier newSoldier = new Soldier(i, 0, 0);
                Soldier.soldierArray[i] = newSoldier;
                Soldier.putLeft(i, j);
            }

            if (order.equals("right")) {
                i = in.nextInt();
                j = in.nextInt();
                Soldier newSoldier = new Soldier(i, 0, 0);
                Soldier.soldierArray[i] = newSoldier;
                Soldier.putRight(i, j);
            }

            if (order.equals("leave")) {
                i = in.nextInt();
                Soldier.leave(i);
            }

            if (order.equals("name")) {
                i = in.nextInt();
                out.println(Soldier.soldierArray[i].left + " " + Soldier.soldierArray[i].right);
            }
        }
        out.close();
    }

}
