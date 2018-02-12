package second_sem.decorating;

public class DecoratorTest {
    public static void main(String[] args) {
        MyArray arr = new MyArray(1, 2, 3, 4, 5, 8);

        //DelimiterDecorator is pointless and redundant as delimiter will be defined
        //by calling print function. I'mo too lazy to remove it

        Decorator c = new SuffixDecorator(new BracketsDecorator(new PrefixDecorator(
                (new BracketsDecorator(arr,
                        "{", "}")), "Elements: "),
                "[", "]"), " size: " + arr.size());

        c.print("_", System.out);

    }
}
