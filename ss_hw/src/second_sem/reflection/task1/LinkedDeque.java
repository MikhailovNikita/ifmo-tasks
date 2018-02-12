package second_sem.reflection.task1;

import java.util.Iterator;

public class LinkedDeque<E> implements Deque<E> {



    private static class Node<E> {
        E info;
        Node<E> previous, next;
    }

    public LinkedDeque(String... params) {
    }

    private Node<E> first = null, last = null;

    private void createFromEmpty(E elem) {
        last = first = new Node<E>() {{
            info = elem;
        }};
    }

    @Override
    public void addFirst(E elem) {
        if (first == null) {
            createFromEmpty(elem);
            return;
        }

        Node<E> temp = new Node<E>() {{
            info = elem;
            next = first;
        }};
        first.previous = temp;
        first = temp;
    }

    @Override
    public E first() {
        if (first == null)
            throw new IndexOutOfBoundsException("Empty deque");
        return first.info;
    }

    @Override
    public E removeFirst() {
        if (first == null)
            throw new IndexOutOfBoundsException("Empty deque");
        E result = first();
        first = first.next;
        return result;
    }

    @Override
    public void addLast(E elem) {
        if (last == null) {
            createFromEmpty(elem);
            return;
        }
        Node<E> tmp = new Node<E>() {{
            info = elem;
            previous = last;
        }};
        last.next = tmp;
        last = tmp;
    }

    @Override
    public E last() {
        return last.info;
    }

    @Override
    public E removeLast() {
        if (last == null)
            throw new IndexOutOfBoundsException("Empty deque");
        E result = last();
        last = last.previous;
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> nextElem = first;

            @Override
            public boolean hasNext() {
                return nextElem != null;
            }

            @Override
            public E next() {
                E result = nextElem.info;
                nextElem = nextElem.next;
                return result;
            }
        };
    }
}
