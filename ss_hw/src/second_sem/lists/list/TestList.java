package second_sem.lists.list;

import com.sun.xml.internal.fastinfoset.algorithm.IntegerEncodingAlgorithm;

import java.util.ArrayList;
import java.util.Random;

public class TestList {
    static Random rnd = new Random();

    public static void main(String[] args) {
        List<Integer> intList = new List<>();
        int size = rnd.nextInt(5) + 5;
        for(int i = 0; i < size; i++){
            int elem = rnd.nextInt(10);
            intList.add(elem);
        }

        System.out.println(intList.toString());

        for(int i = 0; i < 100; i++){
            int first = rnd.nextInt(size);
            int second = rnd.nextInt(size);

            ArrayList<Integer> before = intList.toArrayList();
            swap(first, second, before);
            intList.swap(first, second);
            ArrayList<Integer> after = intList.toArrayList();
            assert (before.size() == after.size());
            for(int j = 0; j < before.size(); j++){
                assert (before.get(j).equals(after.get(j)));
            }

            if((i+1) % 10 == 0) System.out.println((i + 1) + " tests complete");
        }

        for(int i = 0; i < 10; i++){
            int s = rnd.nextInt(5) + 5;
            List<Integer> list = generateList(size);
            ArrayList<Integer> before = list.toArrayList();
            before.sort(Integer::compareTo);
            list.sort();
            ArrayList<Integer> after = list.toArrayList();
            assert (before.size() == after.size());

            for(int j = 0; j < before.size(); j++){
                assert(before.get(j).equals(after.get(j)));
            }

            System.out.println("Sorting test " + (i+1) + " complete");
            System.out.println("Sorted list: " + list.toString());
        }




    }

    public static void swap(int i, int j, ArrayList<Integer> arr){
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    public static List<Integer> generateList(int size){
        List<Integer> list = new List<>();
        for(int i = 0; i < size; i++){
            list.add(rnd.nextInt(10));
        }

        return list;
    }
}
