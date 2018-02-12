package second_sem.decorating;


import java.io.PrintStream;

interface Sequence {
    void print(String separator, PrintStream ps);

    int size();
}
