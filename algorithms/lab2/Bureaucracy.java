import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * Created by mikha on 21.11.2015.
 */
public class Bureaucracy {


    static Queue createQueue(int n, Scanner in) throws Exception {
        People prev;
        Queue q = new Queue();
        People human = new People(in.nextInt());
        q.head = human;
        prev = q.head;
        for (int i = 1; i < n - 1; i++) {
            People h = new People(in.nextInt());
            prev.next = h;
            prev = h;
        }
        People h = new People(in.nextInt());
        prev.next = h;
        q.tail = h;
        return q;
    }

    static void giveForMod(int n, Queue q) {

        People cur = q.head;
        for (int i = 0; i < n; i++) {
            if (--cur.needPapers == 0) {
                q.popHuman(cur);
            } else {
                q.shiftHuman(cur);
            }
            cur = cur.next;
            q.papersLeft--;
        }
    }

    static void giveForDiv(int amount, Queue q) {
        People cur = q.head;
        int k = q.size;
        for (int i = 0; i < k; i++) {
            if ((cur.needPapers -= amount) < 1) {
                q.papersLeft+=-(cur.needPapers);
                q.popHuman(cur);
            }else{
                q.shiftHuman(cur);
            }
            q.papersLeft-=amount;
            cur=cur.next;
        }

    }

    public static class People {
        int needPapers;
        People next;

        public People(int number) {
            this.needPapers = number;
        }
    }

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

    public static class Queue {
        static People head;
        static People tail;
        static int size;
        static int leftAmount;
        static int giveAmount;
        static int papersLeft;

        void popHuman(People human) {
            this.size -= 1;
            Queue.head = Queue.head.next;
            return;
        }

        void shiftHuman(People human) {
            this.tail.next = human;
            this.tail = human;
            this.head = this.head.next;
        }

        int getValue(People human) {
            return human.needPapers;
        }


    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("bureaucracy.in"));
        PrintWriter out = new PrintWriter(new File("bureaucracy.out"));
        int n, m;
        n = in.nextInt();
        m = in.nextInt();

        Queue q = createQueue(n, in);
        q.size = n;
        q.papersLeft = m;

        while(q.papersLeft>0){
            if(q.size<1) break;

                q.giveAmount = q.papersLeft / q.size;
                q.leftAmount = q.papersLeft % q.size;
                if (q.giveAmount == 0) {
                    giveForMod(q.leftAmount, q);
                } else {
                    giveForDiv(q.giveAmount, q);
                }


        }


        if(q.size<1){
            out.println(-1);
        }else{
            out.println(q.size);
            People cur = q.head;
            for(int i = 0;i<q.size;i++){
                out.print(cur.needPapers + " ");
                cur = cur.next;
            }
        }

        out.close();


    }
}
