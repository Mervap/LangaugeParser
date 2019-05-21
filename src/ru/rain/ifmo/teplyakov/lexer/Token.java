package ru.rain.ifmo.teplyakov.lexer;

public class Token {

    public Token(TokenType tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public enum TokenType {
        NUMBER,
        IDENTIFIER,
        OPERATION,
        OPEN_PARENTHESIS,
        CLOSE_PARENTHESIS,
        OPEN_SQUARE_BRACKET,
        CLOSE_SQUARE_BRACKET,
        OPEN_BRACKET,
        CLOSE_BRACKET,
        QUESTION,
        COLON,
        COMMA,
        EMPTY
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "[" + tokenType.toString() + ": " + token + "]";
    }

    private final TokenType tokenType;
    private final String token;
}
