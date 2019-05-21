package ru.rain.ifmo.teplyakov.parser;

import java.util.Map;

public class Number implements TreeNode {

    public Number(Integer val) {
        this.val = val;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) {
        return val;
    }

    @Override
    public TreeNode[] getChildren() {
        return new TreeNode[0];
    }

    private Integer val;
}
