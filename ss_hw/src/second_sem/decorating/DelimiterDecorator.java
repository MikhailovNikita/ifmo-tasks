package second_sem.decorating;

import java.io.PrintStream;

@Deprecated
public class DelimiterDecorator extends Decorator {
    String delim;
    public DelimiterDecorator(Sequence s, String delim) {
        super(s);
        this.delim = delim;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        super.print(delim, ps);
    }
}
