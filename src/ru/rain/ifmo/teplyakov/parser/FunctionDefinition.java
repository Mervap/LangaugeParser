package ru.rain.ifmo.teplyakov.parser;

import java.util.List;

public class FunctionDefinition {

    public FunctionDefinition() {
        this.arguments = null;
        this.body = null;
    }

    public void setFields(List<String> argument, TreeNode body) {
        this.arguments = argument;
        this.body = body;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public TreeNode getBody() {
        return body;
    }

    private List<String> arguments;
    private TreeNode body;
}
