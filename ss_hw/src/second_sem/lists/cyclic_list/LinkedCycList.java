package second_sem.lists.cyclic_list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedCycList<E> implements CycList<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
    }


    private Node<E> head;

    public LinkedCycList(ArrayList<E> array) {
        if (array.size() == 0) {
            head = null;
            return;
        }

        head = new Node<>();
        Node cur = head;

        for (int i = 0; i < array.size() - 1; i++) {
            cur.data = array.get(i);
            cur.next = new Node();
            cur = cur.next;
        }
        cur.data = array.get(array.size() - 1);
        cur.next = head;
    }

    public ArrayList<E> toArrayList() {
        ArrayList<E> arrayList = new ArrayList<>();
        for (E e : this) {
            arrayList.add(e);
        }

        return arrayList;
    }

    @Override
    public void shift(int delta) throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Empty list");
        }

        //shift head to next position delta times
        for (int i = 0; i < delta; i++) {
            head = head.next;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> cur = head;
            Node initialHead = head;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public E next() {
                E data = cur.data;
                cur = cur.next;
                if (cur == initialHead) cur = null;
                return data;
            }
        };
    }

    //Check for equality, if not, shift and try again
    //If shifted length(list1) times and haven't confirmed equality - lists are not equal
    public static <E> boolean listsEqual(CycList<E> list1, CycList<E> list2) {
        int length1 = length(list1);

        if (length1 != length(list2)) return false;

        if (length1 == 0) return true;


        for (int i = 0; i < length1; i++) {
            Iterator iterator1 = list1.iterator();
            Iterator iterator2 = list2.iterator();

            boolean isEqual = true;


            while (iterator1.hasNext() && isEqual)
                isEqual = iterator1.next() == iterator2.next();

            if (isEqual)
                return true;

            try {
                list1.shift(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private static <E> int length(CycList<E> list) {
        int result = 0;
        for (E e : list) {
            result++;
        }
        return result;
    }
}