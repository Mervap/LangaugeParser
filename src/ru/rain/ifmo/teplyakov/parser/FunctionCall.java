package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.RuntimeErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class FunctionCall implements TreeNode {

    public FunctionCall(List<TreeNode> argument, FunctionDefinition functionDefinition) throws ParserException {
        this.arguments = argument;
        this.functionDefinition = functionDefinition;
    }

    public boolean isBadArguments() {
        return arguments.size() != functionDefinition.getArguments().size();
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws RuntimeErrorException {

        Map<String, Integer> newContext = new HashMap<>();
        for (int i = 0; i < functionDefinition.getArguments().size(); ++i) {
            newContext.put(functionDefinition.getArguments().get(i), arguments.get(i).evaluate(context));
        }

        try {
            return functionDefinition.getBody().evaluate(newContext);
        } catch (RuntimeErrorException e) {
            e.setLine(functionDefinition.getLine());
            throw e;
        }
    }

    @Override
    public TreeNode[] getChildren() {
        return arguments.toArray(new TreeNode[arguments.size()]);
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(",", "(", ")");

        for (TreeNode arg : arguments) {
            result.add(arg.toString());
        }

        return functionDefinition.getFunctionName() + result.toString();
    }

    public FunctionDefinition getFunctionDefinition() {
        return functionDefinition;
    }

    private List<TreeNode> arguments;
    private FunctionDefinition functionDefinition;
}
