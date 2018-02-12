package second_sem.decorating;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MyArray implements Sequence{
    private Integer[] array;

    public MyArray(Integer... elements){
        array = elements;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        ps.print(String.join(
                        separator,
                        Arrays.stream(array)
                                .map(Object::toString)
                                .collect(Collectors.toList())));
    }

    @Override
    public int size() {
        return array.length;
    }
}
