import java.io.*;
import java.util.StringTokenizer;

/**
 * Created by Дима1 on 17.12.2015.
 */
public class BST {
    static int max(int a,int b){
        if(a>b){
            return a;
        }else{
            return b;
        }
    }

    static class AVL_Tree{


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
            if(p != null) {
                return p.height;
            }else{
                return 0;
            }
        }

        int bfactor(Node p) {
            return height(p.right) - height(p.left);
        }

        void fixHeight(Node p) {
            int hl = height(p.left);
            int hr = height(p.right);
            p.height = max(hl,hr) + 1;
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
               }else{
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


        String prev(Node p, int x) {
            if (p == null) return "none";
            Node cur = p;
            Node susp = null;
            while (cur != null) {
                if (cur.key < x) {
                    susp = cur;
                    cur = cur.right;
                } else
                    cur = cur.left;
            }
            if (susp == null) return "none";
            return String.valueOf(susp.key);
        }

        String next(Node p, int key) {
            if (p == null) return "none";
            Node cur = p;
            Node susp = null;
            while (cur != null) {
                if (cur.key > key) {
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
        BufferedReader in = new BufferedReader(new FileReader(new File("bst.in")));
        PrintWriter out = new PrintWriter(new File("bst.out"));
        AVL_Tree.Node p = new AVL_Tree.Node();
        AVL_Tree tree = new AVL_Tree();
        String s;
        while ((s = in.readLine()) != null) {
            String[] op = s.split(" ");
            if (op[0].equals("insert")) {
                p = tree.insert(p, Integer.parseInt(op[1]), true);
            }
            if (op[0].equals("delete")) {
                p = tree.remove(p, Integer.parseInt(op[1]), true);
            }
            if (op[0].equals("exists")) {
                out.println(tree.exists(p, Integer.parseInt(op[1])));
            }
            if (op[0].equals("next")) {
                out.println(tree.next(p, Integer.parseInt(op[1])));
            }
            if (op[0].equals("prev")) {
                out.println(tree.prev(p, Integer.parseInt(op[1])));
            }
        }
        in.close();
        out.close();
    }

}


