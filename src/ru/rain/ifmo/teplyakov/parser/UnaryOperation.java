package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.RuntimeErrorException;

import java.util.Map;
import java.util.function.Function;

public class UnaryOperation implements TreeNode {

    UnaryOperation(Function<Integer, Integer> op, TreeNode argument) {
        this.op = op;
        this.argument = argument;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws RuntimeErrorException {
        try {
            return op.apply(argument.evaluate(context));
        } catch (RuntimeException e) {
            throw new RuntimeErrorException(this.toString());
        }
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[]{argument};
    }

    @Override
    public String toString() {
        return "-" + argument.toString();
    }

    private Function<Integer, Integer> op;
    private TreeNode argument;
}
