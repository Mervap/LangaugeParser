package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ParameterNotFoundException;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import java.util.Map;
import java.util.function.Function;

public class UnaryOperation implements TreeNode {

    UnaryOperation(Function<Integer, Integer> op, TreeNode argument) {
        this.op = op;
        this.argument = argument;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) {
        return op.apply(argument.evaluate(context));
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[]{argument};
    }

    private Function<Integer, Integer> op;
    private TreeNode argument;
}
