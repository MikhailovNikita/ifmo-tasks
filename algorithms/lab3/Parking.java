import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * Created by mikha on 21.12.2015.
 */
public class Parking {

    static int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    static class AVL_Tree {


        static class Node {
            int key;
            int height;
            Node left;
            Node right;

            Node() {
                this.left = null;
                this.right = null;
            }

            Node(int key) {
                this.key = key;
                this.left = null;
                this.right = null;
                this.height = 1;

            }

        }

        int height(Node p) {
            if (p != null) {
                return p.height;
            } else {
                return 0;
            }
        }

        int bfactor(Node p) {
            return height(p.right) - height(p.left);
        }

        void fixHeight(Node p) {
            int hl = height(p.left);
            int hr = height(p.right);
            p.height = max(hl, hr) + 1;
        }

        Node rotateR(Node p) {
            Node q = p.left;
            p.left = q.right;
            q.right = p;
            fixHeight(p);
            fixHeight(q);
            return q;
        }

        Node rotateL(Node q) {
            Node p = q.right;
            q.right = p.left;
            p.left = q;
            fixHeight(q);
            fixHeight(p);
            return p;
        }

        Node balance(Node p) {
            fixHeight(p);
            if (bfactor(p) == 2) {
                if (bfactor(p.right) < 0)
                    p.right = rotateR(p.right);
                return rotateL(p);
            }
            if (bfactor(p) == -2) {
                if (bfactor(p.left) > 0)
                    p.left = rotateL(p.left);
                return rotateR(p);
            }
            return p;
        }

        Node insert(Node p, int key, boolean firstIteration) {
            if (p == null) return new Node(key);
            if (p.height == 0) return new Node(key);
            if (firstIteration) {
                if (exists(p, key)) return p;
            }

            if (key < p.key) {
                p.left = insert(p.left, key, false);
            } else {
                p.right = insert(p.right, key, false);
            }

            return balance(p);
        }

        Node findMin(Node p) {
            if (p.left != null) {
                return findMin(p.left);
            } else {
                return p;
            }
        }

        Node removeMin(Node p) {
            if (p.left == null)
                return p.right;
            p.left = removeMin(p.left);
            return balance(p);
        }

        Node remove(Node p, int key, boolean firstIteration) {
            if (firstIteration) {
                if (!(exists(p, key))) return p;
            }
            if (p == null) return null;
            if (key < p.key)
                p.left = remove(p.left, key, false);
            else if (key > p.key)
                p.right = remove(p.right, key, false);
            else {
                Node q = p.left;
                Node r = p.right;
                if (r == null) return q;
                Node min = findMin(r);
                min.right = removeMin(r);
                min.left = q;
                return balance(min);
            }

            return balance(p);
        }

        boolean exists(Node p, int key) {
            if (p == null) return false;
            if (key == p.key) return true;
            if (key < p.key) {
                if (p.left == null) return false;
                return exists(p.left, key);
            } else {
                if (p.right == null) return false;
                return exists(p.right, key);
            }
        }

        String next(Node p, int key) {
            if (p == null) return "none";
            Node cur = p;
            Node susp = null;
            while (cur != null) {
                if (cur.key >= key) {
                    susp = cur;
                    cur = cur.left;
                } else
                    cur = cur.right;
            }
            if (susp == null) return "none";
            return String.valueOf(susp.key);
        }


    }


    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(new File("parking.in")));
        PrintWriter out = new PrintWriter(new File("parking.out"));
        AVL_Tree.Node p = new AVL_Tree.Node();
        AVL_Tree tree = new AVL_Tree();
        String s;
        String[] op = in.readLine().split(" ");
        int n = Integer.parseInt(op[0]);
        int m = Integer.parseInt(op[1]);
        for(int i = 1;i<=n;i++) {
            p = tree.insert(p, i, true);
        }

        for(int i = 0;i<m;i++){
            s = in.readLine();
            op = s.split(" ");
            if (op[0].equals("enter")){
                String del = tree.next(p, Integer.parseInt(op[1]));
                if (del.equals("none")){
                    int d = tree.findMin(p).key;
                    p = tree.remove(p, d, true);
                    out.println(d);
                }else{
                    p = tree.remove(p, Integer.parseInt(del), true);
                    out.println(Integer.parseInt(del));
                }
            }

            if(op[0].equals("exit")){
                p = tree.insert(p,Integer.parseInt(op[1]), true);
            }
        }
        in.close();
        out.close();
    }
}
