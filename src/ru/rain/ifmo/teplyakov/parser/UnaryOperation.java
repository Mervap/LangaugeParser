package ru.rain.ifmo.teplyakov.parser;

import java.util.function.Function;

public class UnaryOperation implements TreeNode {

    UnaryOperation(Function<Integer, Integer> op, TreeNode argument) {
        this.op = op;
        this.argument = argument;
    }

    @Override
    public Integer evaluate() {
        return op.apply(argument.evaluate());
    }

    private Function<Integer, Integer> op;
    private TreeNode argument;
}
