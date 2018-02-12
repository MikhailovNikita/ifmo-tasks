package second_sem.reflection.task2;

import java.lang.reflect.Method;
import java.util.Random;

public class ReflectionSort {
    public static void main(String[] args) {
        Method method = null;
        try{
            method = SortAlgorithms.class.getDeclaredMethod(args[0],Comparable[].class);
        }catch(NoSuchMethodException e){
            System.out.println("No such sort algorithm, use \"standardSort\", \"bubbleSort\" or \"insertionSort\" ");
            e.printStackTrace();
            return;
        }


        Random rnd = new Random();
        int size = rnd.nextInt(10) + 10;
        Integer[] testArray = new Integer[size];
        String[] testStringArray = {"abc" , "ba", "20", "z", "aad", "1"};
        for(int i = 0; i < size; i++){
            testArray[i] = rnd.nextInt(200);
        }
        try{
            method.invoke(new SortAlgorithms(), (Object) testArray);
            method.invoke(new SortAlgorithms(), (Object) testStringArray);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Something wrong");
            return;
        }

        for(int e : testArray){
            System.out.print(e + " ");
        }

        System.out.println();

        for(String s : testStringArray){
            System.out.print(s + " ");
        }



        System.out.println();
        System.out.println(method.getName() + " method was used");

    }
}
