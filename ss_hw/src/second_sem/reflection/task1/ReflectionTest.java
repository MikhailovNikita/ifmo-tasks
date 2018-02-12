package second_sem.reflection.task1;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

public class ReflectionTest {
    private static String path = "second_sem.reflection.task1.";


    public static void main(String[] args) {
        Class<Deque> dequeClass;
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        try {
            //use ArrayDeque or LinkedDeque
            dequeClass = (Class<Deque>) classLoader.loadClass(path + args[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Deque<Integer> deque;
        try {
            deque = (Deque<Integer>) dequeClass.getConstructors()[0].newInstance((Object) args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Integer x = random.nextInt(100);
            Integer y = random.nextInt(100);
            deque.addFirst(x);
            deque.addLast(y);
        }

        deque.removeLast();
        deque.addFirst(30);

        deque.removeFirst();
        deque.addLast(100);


        int sum = 0;
        for (Integer element : deque) {
            System.out.println(element);
            sum += element;
        }
        System.out.println("Elements' sum " + sum);

        try {
            Method size = dequeClass.getMethod("size");
            System.out.println("Elements' amount: " + size.invoke(deque).toString());
        } catch (NoSuchMethodException e) {
            System.out.println("No size method");
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
