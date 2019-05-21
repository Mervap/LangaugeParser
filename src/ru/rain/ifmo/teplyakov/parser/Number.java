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

    private Integer val;
}
