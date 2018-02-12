import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 14.11.2015.
 */
public class Brackets {
    public static void main(String[] args) throws Exception {
        File inputFile = new File("Brackets.in");
        File outputFile = new File("Brackets.out");
        PrintWriter out = new PrintWriter(outputFile);
        BufferedReader br = new BufferedReader(new FileReader(inputFile));

        char[] brackets = new char[10010];
        int head = 0;
        char c = (char) br.read();
        brackets[0] = c;

        if (c != (char) -1) {
            head++;
        }


        while (true) {

            c = (char) br.read();
            if (c == (char) -1) {
                if (head == 0) out.println("YES");
                else out.println("NO");
                break;
            }

            if ((c == '(') || (c == '[') || (c == '{')) {
                brackets[head] = c;
                head++;
            } else {
                if ((head == 0) && ((c == ')') || (c == ']') || (c == '{'))) {
                    out.println("NO");
                    break;
                }
                if (c == ')') {
                    if (brackets[head - 1] == '(') {
                        head--;
                    } else {
                        out.println("NO");

                        break;
                    }
                }
                if (c == ']') {
                    if (brackets[head - 1] == '[') {
                        head--;
                    } else {
                        out.println("NO");
                        break;
                    }
                }
                if (c == '}') {
                    if (brackets[head - 1] == '{') {
                        head--;
                    } else {
                        out.println("NO");
                        break;
                    }
                }
            }
        }

        out.close();
    }
}
