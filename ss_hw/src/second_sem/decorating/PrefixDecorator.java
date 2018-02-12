package second_sem.decorating;

import java.io.PrintStream;

public class PrefixDecorator extends Decorator {
    String prefix;
    public PrefixDecorator(Sequence s, String prefix) {
        super(s);
        this.prefix = prefix;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        ps.print(prefix);
        super.print(separator, ps);
    }
}
