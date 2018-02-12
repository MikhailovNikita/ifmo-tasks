import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 14.11.2015.
 */
public class Decode {
    public static void main(String[] args) throws Exception{
        File inputFile = new File("Decode.in");
        File outputFile = new File("Decode.out");
        PrintWriter out = new PrintWriter(outputFile);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        int[] letters = new int[200010];
        int head = 1;
        int c = br.read();
        letters[0] = c;

       while (c!= -1){
            c =  br.read();
            if ((head>0) && (c == letters[head-1])) {
                head--;
            }else{
                letters[head] = c;
                head++;
            }
        }

        for(int i = 0;i<head-1;i++){
            out.print((char) letters[i]);
        }

        out.close();

    }
}
