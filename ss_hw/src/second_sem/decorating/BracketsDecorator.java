package second_sem.decorating;

import java.io.PrintStream;

public class BracketsDecorator extends Decorator {
    private String leftBorder;
    private String rightBorder;

    public BracketsDecorator(Sequence s, String leftBorder, String rightBorder) {
        super(s);
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    @Override
    public void print(String separator, PrintStream ps) {
        ps.print(leftBorder);
        super.print(separator, ps);
        ps.print(rightBorder);
    }
}
