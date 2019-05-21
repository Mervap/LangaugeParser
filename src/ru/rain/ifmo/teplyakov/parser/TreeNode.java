package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.RuntimeErrorException;

import java.util.Map;

public interface TreeNode {
    Integer evaluate(Map<String, Integer> context) throws RuntimeErrorException;

    TreeNode[] getChildren();
}
