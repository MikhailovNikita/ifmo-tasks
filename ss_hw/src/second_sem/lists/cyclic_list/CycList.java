package second_sem.lists.cyclic_list;

public interface CycList<E> extends Iterable<E> {

    void shift(int delta) throws Exception;
}