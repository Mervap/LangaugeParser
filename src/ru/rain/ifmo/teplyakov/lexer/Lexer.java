package ru.rain.ifmo.teplyakov.lexer;

import ru.rain.ifmo.teplyakov.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public Lexer() throws SyntaxException {
        tokens = new ArrayList<>();
    }

    public boolean hasNext() {
        return pos < tokens.size();
    }

    public List<Token> getNext() {
        if (!hasNext()) {
            return null;
        }

        return tokens.get(pos++);
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
                nextTokenType = Token.TokenType.OPEN_BRACKET;
            } else if (c == ')') {
                nextTokenType = Token.TokenType.CLOSE_BRACKET;
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
    private int pos = 0;
}
