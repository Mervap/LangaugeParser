package ru.rain.ifmo.teplyakov.exception;

public class ParameterNotFoundException extends ParserException {
    public ParameterNotFoundException(String name, int line) {
        super("Parameter not found " + name + ":" + line);
    }
}
