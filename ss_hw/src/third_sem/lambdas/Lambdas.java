package third_sem.lambdas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Lambdas {
    private static final Map<Character, BinaryOperator<Integer>> operatorMap =
            new HashMap<Character, BinaryOperator<Integer>>() {{
                put('+', (a, b) -> a + b);
                put('-', (a, b) -> a - b);
                put('*', (a, b) -> a * b);
                put('/', (a, b) -> a / b);
            }};

    public static void main(String[] args) {
        Node<Integer, Character> tree = new BiNode<>(
                '-', new BiNode<>(
                        '+',
                        new Leaf<>(10),
                        new Leaf<>(-3)
                ),
                new BiNode<>(
                        '*',
                        new Leaf<>(4),
                        new BiNode<>('/', new Leaf(20), new Leaf(3))
                )
        );
        System.out.println("Tree evaluating");
        System.out.println(evaluate(tree));

        System.out.println("Invert feature test");
        System.out.println(toString(invertSign(tree, (e) -> -e)));
    }

    public static Integer evaluate(Node<Integer, Character> tree) {
        return forceEvaluation(tree, operatorMap);
    }

    public static <Num, Operation> Num forceEvaluation(Node<Num, Operation> tree, Map<Operation, BinaryOperator<Num>> operatorMap) {
        return tree.process(
                (e) -> e,
                (arg1, arg2, arg3) -> operatorMap.get(arg1).apply(arg2, arg3));
    }

    public static <Num, Operation> String toString(Node<Num, Operation> tree) {
        return tree.process(
                Num::toString,
                ((arg1, arg2, arg3) -> "(" + arg2 + " " + arg1 + " " + arg3 + ")"));
    }

    public static <Num, Operation> Node<Num, Operation> invertSign(Node<Num, Operation> tree, UnaryOperator<Num> negate) {
        return tree.process(
                //for leafs create new one with negated value
                (e) -> new Leaf<>(negate.apply(e)),
                //for parents create new one from arguments
                (TreeFunction<Operation, Node<Num, Operation>>) BiNode::new);
    }
}
