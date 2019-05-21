package ru.rain.ifmo.teplyakov.exception;

public class FunctionNotFoundException extends ParserException {
    public FunctionNotFoundException(String name, int line) {
        super("Function not found " + name + ":" + line);
    }
}
