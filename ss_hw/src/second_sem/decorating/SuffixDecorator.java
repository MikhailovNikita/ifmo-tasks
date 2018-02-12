package second_sem.decorating;

import java.io.PrintStream;

public class SuffixDecorator extends Decorator {
    String suffix;
    public SuffixDecorator(Sequence s, String suffix) {
        super(s);
        this.suffix = suffix;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        super.print(separator, ps);
        ps.print(suffix);
    }
}
