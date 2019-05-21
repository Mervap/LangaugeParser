package ru.rain.ifmo.teplyakov.parser;

public class Number implements TreeNode {

    public Number(Integer val) {
        this.val = val;
    }

    @Override
    public Integer evaluate() {
        return val;
    }

    private Integer val;
}
