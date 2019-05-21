package ru.rain.ifmo.teplyakov.exception;

public class SyntaxException extends ParserException {
    public SyntaxException() {
        super();
    }

    public SyntaxException(String message) {
        super(message);
    }
}
