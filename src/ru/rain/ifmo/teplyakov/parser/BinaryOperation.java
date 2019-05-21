package ru.rain.ifmo.teplyakov.parser;

import java.util.function.BinaryOperator;

public class BinaryOperation implements TreeNode {

    BinaryOperation(BinaryOperator<Integer> op, TreeNode left, TreeNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer evaluate() {
        return op.apply(left.evaluate(), right.evaluate());
    }

    private BinaryOperator<Integer> op;
    private TreeNode left;
    private TreeNode right;
}
