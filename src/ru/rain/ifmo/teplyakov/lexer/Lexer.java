package ru.rain.ifmo.teplyakov.lexer;

import ru.rain.ifmo.teplyakov.exception.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public Lexer() {
        tokens = new ArrayList<>();
    }

    public List<List<Token>> getLines() {
        return tokens;
    }

    public void addTokens(String line) throws SyntaxException {
        List<Token> lineTokens = new ArrayList<>();
        Token.TokenType curTokenType = Token.TokenType.EMPTY;
        Token.TokenType nextTokenType;

        StringBuilder token = new StringBuilder();
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (Character.isDigit(c)) {
                nextTokenType = Token.TokenType.NUMBER;
            } else if (Character.isLetter(c) || c == '_') {
                nextTokenType = Token.TokenType.IDENTIFIER;
            } else if (isOperation(c)) {
                nextTokenType = Token.TokenType.OPERATION;
            } else if (c == '(') {
                nextTokenType = Token.TokenType.OPEN_PARENTHESIS;
            } else if (c == ')') {
                nextTokenType = Token.TokenType.CLOSE_PARENTHESIS;
            } else if (c == '[') {
                nextTokenType = Token.TokenType.OPEN_SQUARE_BRACKET;
            } else if (c == ']') {
                nextTokenType = Token.TokenType.CLOSE_SQUARE_BRACKET;
            } else if (c == '{') {
                nextTokenType = Token.TokenType.OPEN_BRACKET;
            } else if (c == '}') {
                nextTokenType = Token.TokenType.CLOSE_BRACKET;
            } else if (c == '?') {
                nextTokenType = Token.TokenType.QUESTION;
            } else if (c == ':') {
                nextTokenType = Token.TokenType.COLON;
            } else if (c == ',') {
                nextTokenType = Token.TokenType.COMMA;
            } else {
                throw new SyntaxException("Syntax error");
            }

            if (token.length() > 0 &&
                    !((curTokenType.equals(Token.TokenType.NUMBER)
                            || curTokenType.equals(Token.TokenType.IDENTIFIER))
                            && curTokenType.equals(nextTokenType))) {
                lineTokens.add(new Token(curTokenType, token.toString()));
                token = new StringBuilder();
            }
            token.append(c);
            curTokenType = nextTokenType;
        }

        if (token.length() > 0) {
            lineTokens.add(new Token(curTokenType, token.toString()));
        }

        tokens.add(lineTokens);
    }

    private boolean isOperation(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '>' || c == '<' || c == '=');
    }

    private List<List<Token>> tokens;
}
