import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 02.01.2016.
 */
public class River {
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("river.in"));
        PrintWriter out = new PrintWriter(new File("river.out"));
        int n = Integer.parseInt(in.readLine().split(" ")[0]);
        String[] lengths = new String[n];
        lengths = in.readLine().split(" ");
        for(int i = 0;i<n;i++){

        }
    }

    public class Treap{

    }
}
