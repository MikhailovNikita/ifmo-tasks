package third_sem.lambdas;

import java.util.function.Function;

public class BiNode<V, T> implements Node<V, T> {
    private T binInfo;
    private Node<V, T> left, right;

    public BiNode(T info, Node<V, T> left, Node<V, T> right) {
        binInfo = info;
        this.left = left;
        this.right = right;
    }

    public <R> R process(Function<V, R> leafProcessor,
                         TreeFunction<T, R> biNodeProcessor) {
        R leftRes = left.process(leafProcessor, biNodeProcessor);
        R rightRes = right.process(leafProcessor, biNodeProcessor);
        return biNodeProcessor.apply(binInfo, leftRes, rightRes);
    }
}