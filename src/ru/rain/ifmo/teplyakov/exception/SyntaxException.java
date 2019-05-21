package ru.rain.ifmo.teplyakov.exception;

public class SyntaxException extends ParserException {
    public SyntaxException() {
        super("Syntax Error");
    }
}
