package ru.rain.ifmo.teplyakov.parser;

import java.util.Map;

public class Variable implements TreeNode {

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) {
        return context.get(var);
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[0];
    }

    public String getVar() {
        return var;
    }

    @Override
    public String toString() {
        return var;
    }

    private String var;
}
