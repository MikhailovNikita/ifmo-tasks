package second_sem.lists.list;



import java.util.ArrayList;
import java.util.Objects;

public class List<E extends Comparable<E>> {
    private static class Node<E> {
        E data;
        Node<E> next;
    }

    private Node<E> fakeHead = null;
    private Node<E> last = null;


    public List() {

    }

    public void add(E e){
        Node<E> curNode = new Node<>();
        curNode.data = e;

        if(Objects.isNull(fakeHead)){
            fakeHead = new Node<>();
            fakeHead.next = curNode;
            last = curNode;
        }else{
            last.next = curNode;
            last = curNode;
        }

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node cur = fakeHead;
        while(cur.next != null){
            cur = cur.next;
            sb.append(cur.data);
            sb.append(" ");
        }

        return sb.toString();
    }


    public ArrayList<E> toArrayList() {
        ArrayList<E> arrayList = new ArrayList<>();
        for (Node<E> elem = fakeHead.next; elem != null; elem = elem.next) {
            arrayList.add(elem.data);
        }
        return arrayList;
    }



    public void swap(int firstPosition, int secondPosition) {
        if(firstPosition == secondPosition) return;

        if (firstPosition > secondPosition) {
            int tmp = firstPosition;
            firstPosition = secondPosition;
            secondPosition = tmp;
        }

        Node<E> beforeFirst = null;
        Node<E> beforeSecond = null;
        Node<E> cur = fakeHead;
        int index = -1;


        while (cur.next != null ||( beforeFirst == null && beforeSecond == null)) {
            if (index == firstPosition - 1) {
                beforeFirst = cur;
            }
            if (index == secondPosition - 1) {
                beforeSecond = cur;
            }
            index++;
            cur = cur.next;
        }

        //reached the end of list and haven't found nodes
        if (beforeFirst == null || beforeSecond == null) {
            throw new IndexOutOfBoundsException();
        }


        Node<E> first = beforeFirst.next;
        Node<E> second = beforeSecond.next;

        // If they stand near
        if (secondPosition - firstPosition == 1) {
            beforeFirst.next = second;
            first.next = second.next;
            second.next = first;
            return;
        }


        Node<E> temp1 = first.next;
        first.next = second.next;
        second.next = temp1;


        Node<E> temp2 = beforeFirst.next;
        beforeFirst.next = beforeSecond.next;
        beforeSecond.next = temp2;
    }

    public void sort() {
        //insertion sort algorithm is used
        int sortedLength = 1;
        Node<E> head = fakeHead.next;
        //nothing to do with empty list
        if (head == null) return;


        Node<E> curNode = head.next;
        while (curNode != null) {
            Node<E> temp = curNode.next;
            Node<E> firstGreaterElement = fakeHead;
            int firstGreaterIndex = 0;
            // search for first greater
            while (firstGreaterElement.next != null && curNode.data.compareTo(firstGreaterElement.next.data) > 0) {
                firstGreaterIndex++;
                firstGreaterElement = firstGreaterElement.next;
            }

            //trick to insert the element and shift all the others
            while (firstGreaterIndex <= sortedLength) {
                try {
                    swap(sortedLength, firstGreaterIndex);
                } catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    return;
                }
                firstGreaterIndex++;
            }

            curNode = temp;
            sortedLength++;
        }
    }
}