import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by mikha on 28.02.2016.
 */
public class Palindrome {
    static class Pair {
        int i;
        int j;

        Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    public static void printPalindrome(int i,int j,Pair[][] p,char[] word,PrintWriter out){
        if ((i == 0) || (j == 0)) {
            return;
        }
        if ((p[i][j].i == i - 1) && (p[i][j].j == j - 1)) {
            printPalindrome(i - 1, j - 1, p, word, out);
            out.print(word[i]);
        } else {
            if ((p[i][j].i == i - 1) && (p[i][j].j == j)) {
                printPalindrome(i - 1, j, p, word, out);
            } else {
                printPalindrome(i, j - 1, p, word, out);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("palindrome.in")));
        PrintWriter out = new PrintWriter(new File("palindrome.out"));
        String s = in.readLine();
        //String r = new StringBuffer(s).reverse().toString();
        char[] word = new char[s.length()+1];
        char[] rword = new char[s.length()+1];
        for(int i = 1;i<word.length;i++){
            word[i] = s.charAt(i-1);
            rword[word.length-i]=word[i];
        }

        Pair[][] p = new Pair[word.length][rword.length];
        int[][] d = new int[word.length][rword.length];
        int maxLength = 0;

        for (int i = 1; i < d.length; i++) {
            for (int j = 1; j < d[i].length; j++) {
                if ((j == 0) || (i == 0)) {
                    d[i][j] = 0;
                } else {
                    if (word[i] == rword[j]) {
                        d[i][j] = d[i - 1][j - 1] + 1;
                        p[i][j] = new Pair(i - 1, j - 1);
                        if (d[i][j] > maxLength) {
                            maxLength = d[i][j];
                        }
                    } else {
                        if (d[i - 1][j] < d[i][j - 1]) {
                            d[i][j] = d[i][j - 1];
                            p[i][j] = new Pair(i, j - 1);

                        } else {
                            d[i][j] = d[i - 1][j];
                            p[i][j] = new Pair(i - 1, j);
                        }
                    }
                }
            }
        }
        out.println(maxLength);
        int[] ans = new int[maxLength];
        printPalindrome(word.length - 1, rword.length - 1, p, word, out);
        out.close();
    }
}
