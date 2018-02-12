package second_sem.reflection.task1;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {

    private E[] array;
    private int first = 1, last = 0;
    private int maxSize = 16;

    public ArrayDeque(String... params) {
        maxSize = Integer.parseInt(params[1]);
        resize(true);
    }


    private boolean resize(boolean freeFirst) {
        int len = last - first + 1;
        if (len >= maxSize) return false;
        E[] temp = (E[]) Array.newInstance(Object.class, Math.min(maxSize,(len + 2) * 2));
        int j = 0;
        int gap;
        if((temp.length - len) % 2 == 0){
            gap = (temp.length - len) / 2;
        }else{
            if(freeFirst){
                gap = (temp.length - len) / 2 + 1;
            }else{
                gap = (temp.length - len) / 2;
            }
        }



        for (int i = first; i <= last; i++) {
            temp[gap + (j++)] = array[i];
        }

        array = temp;
        first = gap;
        last = gap + j - 1;
//        first = gap;
//        last = gap + j - 1;

        //System.out.println("Gap: " + gap + " len: " + len + " temp.length: " + temp.length
        // + " last: " + last);


        return true;
    }

    public int size() {
        return last - first + 1;
    }

    @Override
    public void addFirst(E elem) {
        //System.out.println("Add first " + elem + " first: " + first + " last:" + last);
        if (first == 0) {
            if(!resize(true)) return;
        }

        array[--first] = elem;
    }

    @Override
    public E first() {
        if (last < first)
            throw new IndexOutOfBoundsException("get from empty");
        return array[0];
    }

    @Override
    public E removeFirst() {
        if (last < first)
            throw new IndexOutOfBoundsException("remove from empty");
        return array[first++];
    }

    @Override
    public void addLast(E elem) {
        //System.out.println("Add last " + elem + " first: " + first + " last:" + last);
        if (last + 1 >= array.length) {
            if(!resize(false)) return;
        }
        array[++last] = elem;
    }

    @Override
    public E last() {
        if (last < first)
            throw new IndexOutOfBoundsException("Empty deque");
        return array[last];
    }

    @Override
    public E removeLast() {
        if (last < first)
            throw new IndexOutOfBoundsException("Empty deque");
        return array[last--];
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int nextPosition = first;

            @Override
            public boolean hasNext() {
                return nextPosition <= last;
            }

            @Override
            public E next() {
                return array[nextPosition++];
            }
        };
    }



}
