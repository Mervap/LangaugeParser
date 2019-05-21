package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ParameterNotFoundException;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import java.util.Map;

public class IfExpression implements TreeNode {

    public IfExpression(TreeNode condition, TreeNode trueBranch, TreeNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context)  {
        return condition.evaluate(context) == 0 ? falseBranch.evaluate(context) : trueBranch.evaluate(context);
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[]{condition, trueBranch, falseBranch};
    }

    private TreeNode condition;
    private TreeNode trueBranch;
    private TreeNode falseBranch;
}
