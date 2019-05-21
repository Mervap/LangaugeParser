package ru.rain.ifmo.teplyakov.parser;

import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import ru.rain.ifmo.teplyakov.exception.ParameterNotFoundException;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.function.BinaryOperator;

public class BinaryOperation implements TreeNode {

    BinaryOperation(BinaryOperator<Integer> op, TreeNode left, TreeNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws ParserException {
        return op.apply(left.evaluate(context), right.evaluate(context));
    }

    private BinaryOperator<Integer> op;
    private TreeNode left;
    private TreeNode right;
}
