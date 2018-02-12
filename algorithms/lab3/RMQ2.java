import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 18.12.2015.
 */
public class  RMQ2 {
    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new FileReader("rmq2.in"));
        PrintWriter out = new PrintWriter(new File("rmq2.out"));

        int n = Integer.parseInt(in.readLine());

        Seg_Tree tree = new Seg_Tree(n);
        String[] numbers = in.readLine().split(" ");
        String s;
        for(int i=0; i<n; i++){
            tree.addFirst(i,Long.parseLong(numbers[i]));
        }
        tree.build();

        while ((s = in.readLine())!= null){
            String[] cmd= s.split(" ");
            switch (cmd[0].charAt(0)){
                case 's':{
                    tree.set(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
                    break;
                }
                case 'a': {
                    tree.add(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2]),Long.parseLong(cmd[3]));
                    break;
                }
                case 'm': {
                    out.println(tree.min(Integer.parseInt(cmd[1]),Integer.parseInt(cmd[2])));
                }
            }
        }
        in.close();
        out.close();
    }

    static class Seg_Tree {
        static class Node {
            long value;
            boolean isLeaf;
            boolean hasColor;
            boolean added;
            long addedValue;
            long color;

            Node(long a) {
                value = a;
                isLeaf = false;
                hasColor = false;
                added = false;
            }

            void makeLeaf() {
                isLeaf = true;
            }

            void clearColor() {
                hasColor = false;
            }

            void unAdded() {
                added = false;
                addedValue = 0;
            }

            void set(long x) {
                if (isLeaf) {
                    value = x;
                    return;
                }
                if (added)
                    unAdded();

                color = x;
                hasColor = true;
                value = x;
            }

            void add(long x) {
                if (isLeaf) {
                    value += x;
                    return;
                }
                if (hasColor) {
                    color += x;
                    value += x;
                    return;
                }
                addedValue += x;
                added = true;
                value += x;
            }

        }

        int n;
        Node list[];

        Seg_Tree(int n) {
            this.n = 1;
            while (this.n < n) {
                this.n *= 2;
            }
            list = new Node[this.n * 2];
            for (int i = this.n+n-1; i < (this.n * 2); i++)
                list[i] = new Node(Long.MAX_VALUE);
        }

        void addFirst(int num, long x){
            list[n+num]=new Node(x);
            list[n+num].makeLeaf();
        }

        void build(){

            buildR(1, n, 2*n-1);
        }

        void buildR(int v, int tl, int tr) {

            if (tl != tr) {

                int tm = (tl + tr) / 2;
                buildR(v * 2, tl, tm);
                buildR(v * 2 + 1, tm + 1, tr);

                list[v]=new Node(Math.min(list[v * 2].value, list[v * 2 + 1].value));
            }
        }
        void push(int v) {
            if (list[v].isLeaf) return;
            if (list[v].hasColor) {
                list[2 * v].set(list[v].color);
                list[2 * v + 1].set(list[v].color);
                list[v].clearColor();
            }
            if (list[v].added) {
                list[v * 2].add(list[v].addedValue);
                list[v * 2 + 1].add(list[v].addedValue);
                list[v].unAdded();
            }
        }

        Node compareNodes(Node a, Node b) {
            if (a.value >= b.value)
                return b;
            else
                return a;
        }

        long min(int l, int r) {
            return minR(1,n, 2*n-1, n+l-1, n+r-1).value;
        }


        Node minR(int v, int tl, int tr, int l, int r) {
            if (l > r) return new Node(Long.MAX_VALUE);
            if (tl == l && tr == r) return list[v];
            push(v);
            int tm = (tl + tr) / 2;
            Node left = minR(v * 2, tl, tm, l, Math.min(tm, r));
            Node right = minR(v * 2 + 1, tm + 1, tr, Math.max(tm + 1, l), r);
            return compareNodes(left,right);
        }

        void set(int l, int r, long x){
            setR(1,n,2*n-1,n+l-1,n+r-1,x);
        }

        void setR(int v, int tl, int tr, int l, int r, long value){
            if(l>r) return;
            if(l==tl && r==tr){
                list[v].set(value);
                return;
            }
            push(v);
            int tm=(tl+tr)/2;
            setR(v*2,tl,tm, l,Math.min(r,tm), value);
            setR(v*2+1,tm+1,tr,Math.max(l,tm+1),r,value);
            list[v].value=compareNodes(list[2*v], list[2*v+1]).value;
        }

        void add(int l, int r, long x){
            addR(1,n,2*n-1,n+l-1,n+r-1,x);
        }

        void addR(int v, int lb, int rb, int l, int r, long value){
            if(l>r) return;
            if(lb==l && rb==r){
                list[v].add(value);
                return;
            }
            push(v);
            int tm=(lb+rb)/2;
            addR(v*2,lb,tm,l,Math.min(r,tm),value);
            addR(v*2+1,tm+1,rb,Math.max(tm+1,l),r,value);
            list[v].value=compareNodes(list[2*v], list[2*v+1]).value;
        }
    }
}