package second_sem.reflection.task2;

import java.util.Arrays;

public class SortAlgorithms {
    public static <E extends Comparable<E>> void standardSort(E[] array){
        Arrays.sort(array);
    }

    public static <E extends Comparable<E>> void bubbleSort(E[] array){
        for(int i = 1; i < array.length; i++)
        {
            for(int j = i; (j >= 1) && ((array[j].compareTo(array[j - 1])) > 0); j--)
            {
                E temp = array[j];
                array[j] = array[j-1];
                array[j-1] = temp;
            }
        }
    }

    public static <E extends Comparable<E>> void insertionSort(E[] array){
        int j;
        E temp;
        for(int i = 0; i < array.length - 1; i++){
            if (array[i].compareTo(array[i + 1]) > 0) {
                temp = array[i + 1];
                array[i + 1] = array[i];
                j = i;
                while (j > 0 && temp.compareTo(array[j - 1]) < 0) {
                    array[j] = array[j - 1];
                    j--;
                }
                array[j] = temp;
            }
        }
    }
}
