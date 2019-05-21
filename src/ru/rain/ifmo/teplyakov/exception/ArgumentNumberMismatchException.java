package ru.rain.ifmo.teplyakov.exception;

public class ArgumentNumberMismatchException extends ParserException {
    public ArgumentNumberMismatchException(String name, int line) {
        super("Argument number mismatch " + name + ":" + line);
    }
}
