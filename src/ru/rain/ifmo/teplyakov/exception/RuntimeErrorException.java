package ru.rain.ifmo.teplyakov.exception;

public class RuntimeErrorException extends LanguageException {

    public RuntimeErrorException(String expression, int line) {
        this.expression = expression;
        this.line = line;
    }

    public RuntimeErrorException(String expression) {
        this(expression, -1);
    }

    public void setLine(int i) {
        if (line == -1) {
            line = i;
        }
    }

    @Override
    public String getMessage() {
        return "Runtime error " + expression + ":" + line;
    }

    private String expression;
    private int line;
}
