package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ParameterNotFoundException;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import java.util.Map;

public interface TreeNode {
    Integer evaluate(Map<String, Integer> context);
    TreeNode[] getChildren();
}
