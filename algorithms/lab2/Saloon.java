import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by mikha on 16.11.2015.
 */
public class Saloon {

    static class Queue {
        int size;
        int beginTime;

        Queue() {
            size = 0;
        }


        String add(int curTime, int endurance) {
            if (size == 0) {
                beginTime = curTime;
                size++;
                return ((beginTime + 20) / 60 + " " + (beginTime + 20) % 60);
            } else {
                if (curTime - beginTime < 20) {
                    return addNotFree(curTime, endurance);
                } else {
                    size = size - ((curTime - beginTime) / 20);
                    beginTime += 20 * ((curTime - beginTime) / 20);
                    if (size <= 0) {
                        size = 0;
                        beginTime = curTime;
                    }
                    return addNotFree(curTime, endurance);
                }
            }
        }

        String addNotFree(int curTime, int endurance) {
            if (endurance >= size) {
                size++;
                int res = beginTime + size * 20;
                return (res / 60 + " " + res % 60);
            } else
                return curTime / 60 + " " + curTime % 60;
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(new File("saloon.in"));
        PrintWriter out = new PrintWriter(new File("saloon.out"));
        Queue q = new Queue();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            out.println(q.add(sc.nextInt() * 60 + sc.nextInt(), sc.nextInt()));
        }
        sc.close();
        out.close();
    }


}

