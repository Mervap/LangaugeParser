package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.ArgumentNumberMismatchException;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCall implements TreeNode {

    public FunctionCall(List<TreeNode> argument, FunctionDefinition functionDefinition) throws ParserException {

       /* if (argument.size() != functionDefinition.getArguments().size()) {
            throw new ArgumentNumberMismatchException("");
        }*/

        this.arguments = argument;
        this.functionDefinition = functionDefinition;
    }

    @Override
    public Integer evaluate(Map<String, Integer> context) throws ParserException {

        if (arguments.size() != functionDefinition.getArguments().size()) {
            throw new ArgumentNumberMismatchException("");
        }

        Map<String, Integer> newContext = new HashMap<>();
        for (int i = 0; i < functionDefinition.getArguments().size(); ++i) {
            newContext.put(functionDefinition.getArguments().get(i), arguments.get(i).evaluate(context));
        }

        return functionDefinition.getBody().evaluate(newContext);
    }

    private List<TreeNode> arguments;
    private FunctionDefinition functionDefinition;
}
