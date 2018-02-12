package second_sem.lists.cyclic_list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Random;

public class TestCycList {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Random rnd = new Random();

        for(int j = 0; j < 100; j++){
            int size = rnd.nextInt(100) + 10;
            ArrayList<Integer> array = new ArrayList<>();
            for(int i = 0; i < size; i++) array.add(rnd.nextInt(50) - 25);
            LinkedCycList<Integer> cycList1 = new LinkedCycList<>(array);
            LinkedCycList<Integer> cycList2 = new LinkedCycList<>(array);
            try{
                cycList1.shift(rnd.nextInt(2 * size) + 1);
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }

            assert LinkedCycList.listsEqual(cycList1, cycList2);
        }


        for(int j = 0; j < 100; j++){
            int size = rnd.nextInt(100) + 10;
            ArrayList<Integer> array1 = new ArrayList<>();
            ArrayList<Integer> array2;
            for(int i = 0; i < size; i++) array1.add(rnd.nextInt(50) - 25);
            array2 = (ArrayList<Integer>) array1.clone();
            Collections.reverse(array2);
            LinkedCycList<Integer> cycList1 = new LinkedCycList<>(array1);
            LinkedCycList<Integer> cycList2 = new LinkedCycList<>(array2);
            try{
                cycList1.shift(rnd.nextInt(2 * size) + 1);
            }catch (NoSuchElementException e){
                e.printStackTrace();
            }

            assert !LinkedCycList.listsEqual(cycList1, cycList2);
        }

    }
}
