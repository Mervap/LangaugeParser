package ru.rain.ifmo.teplyakov.parser;

import java.util.List;

public class FunctionDefinition {

    public FunctionDefinition(String functionName, int line) {
        this.functionName = functionName;
        this.line = line;
        this.arguments = null;
        this.body = null;
    }

    public void setFields(List<String> argument, TreeNode body) {
        this.arguments = argument;
        this.body = body;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public TreeNode getBody() {
        return body;
    }

    public int getLine() {
        return line;
    }

    private String functionName;
    private int line;
    private List<String> arguments;
    private TreeNode body;
}
