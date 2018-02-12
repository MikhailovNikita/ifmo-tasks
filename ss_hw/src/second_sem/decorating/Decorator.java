package second_sem.decorating;

import java.io.PrintStream;

abstract class Decorator implements Sequence {
    protected Sequence sequence;

    public Decorator(Sequence s){
        sequence = s;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        sequence.print(separator, ps);
    }

    @Override
    public int size(){
        return sequence.size();
    }
}
