package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.SyntaxException;
import ru.rain.ifmo.teplyakov.lexer.Lexer;
import ru.rain.ifmo.teplyakov.lexer.Token;

import java.util.List;

public class Parser {

    public Parser(Lexer lexer) throws SyntaxException {
        List<Token> line = lexer.getNext();
        root = parseArgument(line);
        if (line.size() > pos) {
            throw new SyntaxException();
        }
    }

    public TreeNode getTree() {
        return root;
    }

    private TreeNode parseArgument(List<Token> tokens) throws SyntaxException {
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        TreeNode arg;
        Token curToken = getNext(tokens);
        if (curToken.getTokenType().equals(Token.TokenType.OPEN_PARENTHESIS)) {
            arg = parseBinaryOperation(tokens);
        } else if (curToken.getTokenType().equals(Token.TokenType.OPEN_SQUARE_BRACKET)) {
            arg = parseIfExpression(tokens);
        } else if (curToken.getTokenType().equals(Token.TokenType.NUMBER)) {
            arg = new Number(Integer.valueOf(curToken.getToken()));
        } else if (curToken.getTokenType().equals(Token.TokenType.OPERATION)
                && curToken.getToken().equals("-")) {
            TreeNode subArg = parseArgument(tokens);
            arg = new UnaryOperation(a -> -a, subArg);
        } else {
            throw new SyntaxException();
        }

        return arg;
    }

    private Token getNext(List<Token> tokens) throws SyntaxException {
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        return tokens.get(pos++);
    }

    private boolean nextNotEqual(List<Token> tokens, Token.TokenType tokenType) throws SyntaxException {
        return !getNext(tokens).getTokenType().equals(tokenType);
    }

    private TreeNode parseBinaryOperation(List<Token> tokens) throws SyntaxException {
        TreeNode left = parseArgument(tokens);
        Token opToken = getNext(tokens);
        if (!opToken.getTokenType().equals(Token.TokenType.OPERATION)) {
            throw new SyntaxException();
        }
        TreeNode right = parseArgument(tokens);

        if (nextNotEqual(tokens, Token.TokenType.CLOSE_PARENTHESIS)) {
            throw new SyntaxException();
        }

        TreeNode result;
        switch (opToken.getToken()) {
            case "+":
                result = new BinaryOperation(Integer::sum, left, right);
                break;
            case "-":
                result = new BinaryOperation((a, b) -> a - b, left, right);
                break;
            case "*":
                result = new BinaryOperation((a, b) -> a * b, left, right);
                break;
            case "/":
                result = new BinaryOperation((a, b) -> a / b, left, right);
                break;
            case "%":
                result = new BinaryOperation((a, b) -> a % b, left, right);
                break;
            case ">":
                result = new BinaryOperation((a, b) -> a > b ? 1 : 0, left, right);
                break;
            case "<":
                result = new BinaryOperation((a, b) -> a < b ? 1 : 0, left, right);
                break;
            case "=":
                result = new BinaryOperation((a, b) -> a.equals(b) ? 1 : 0, left, right);
                break;
            default:
                throw new SyntaxException("Unknown binop");
        }

        return result;
    }

    private TreeNode parseIfExpression(List<Token> tokens) throws SyntaxException {
        TreeNode condition = parseArgument(tokens);

        Token.TokenType[] tmp =
                {Token.TokenType.CLOSE_SQUARE_BRACKET,
                        Token.TokenType.QUESTION,
                        Token.TokenType.OPEN_BRACKET,
                        Token.TokenType.CLOSE_BRACKET,
                        Token.TokenType.COLON,
                        Token.TokenType.OPEN_BRACKET,
                        Token.TokenType.CLOSE_BRACKET};

        int cur = 0;
        for (; cur < 3; ++cur) {
            if (nextNotEqual(tokens, tmp[cur])) {
                throw new SyntaxException();
            }
        }

        TreeNode trueBranch = parseArgument(tokens);
        for (; cur < 6; ++cur) {
            if (nextNotEqual(tokens, tmp[cur])) {
                throw new SyntaxException();
            }
        }
        TreeNode falseBranch = parseArgument(tokens);
        if (nextNotEqual(tokens, tmp[cur])) {
            throw new SyntaxException();
        }

        return new IfExpression(condition, trueBranch, falseBranch);
    }

    //private Map<String, TreeNode> functions
    private TreeNode root;
    private int pos = 0;
}
