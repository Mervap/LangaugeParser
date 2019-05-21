package ru.rain.ifmo.teplyakov.parser;

public class IfExpression implements TreeNode {

    public IfExpression(TreeNode condition, TreeNode trueBranch, TreeNode falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public Integer evaluate() {
        return condition.evaluate() == 0 ? falseBranch.evaluate() : trueBranch.evaluate();
    }

    private TreeNode condition;
    private TreeNode trueBranch;
    private TreeNode falseBranch;
}
