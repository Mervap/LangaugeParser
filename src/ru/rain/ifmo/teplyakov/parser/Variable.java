package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ParameterNotFoundException;

import java.util.Map;

public class Variable implements TreeNode {

    public Variable(String var) {
        this.var = var;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws ParameterNotFoundException {
        if (!context.containsKey(var)) {
            throw new ParameterNotFoundException(var);
        }
        return context.get(var);
    }

    public String getVar() {
        return var;
    }

    private String var;
}
