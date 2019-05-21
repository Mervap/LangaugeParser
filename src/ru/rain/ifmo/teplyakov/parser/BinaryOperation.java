package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.RuntimeErrorException;

import java.util.Map;
import java.util.function.BinaryOperator;

public class BinaryOperation implements TreeNode {

    BinaryOperation(BinaryOperator<Integer> op, String stringOp, TreeNode left, TreeNode right) {
        this.op = op;
        this.stringOp = stringOp;
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws RuntimeErrorException {
        try {
            return op.apply(left.evaluate(context), right.evaluate(context));
        } catch (RuntimeException e) {
            throw new RuntimeErrorException(this.toString());
        }
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[]{left, right};
    }

    @Override
    public String toString() {
        return "(" + left.toString() + stringOp + right.toString() + ")";
    }

    private BinaryOperator<Integer> op;
    private String stringOp;
    private TreeNode left;
    private TreeNode right;
}
