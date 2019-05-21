package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.lexer.Lexer;
import ru.rain.ifmo.teplyakov.lexer.Token;
import ru.rain.ifmo.teplyakov.SyntaxException;

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
        if (curToken.getTokenType().equals(Token.TokenType.OPEN_BRACKET)) {
            arg = parseBinaryOperation(tokens);
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

    private Token getNext(List<Token> tokens) throws SyntaxException{
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        return tokens.get(pos++);
    }

    private TreeNode parseBinaryOperation(List<Token> tokens) throws SyntaxException {
        TreeNode left = parseArgument(tokens);
        Token opToken = getNext(tokens);
        if (!opToken.getTokenType().equals(Token.TokenType.OPERATION)) {
            throw new SyntaxException();
        }
        TreeNode right = parseArgument(tokens);

        if (!getNext(tokens).getTokenType().equals(Token.TokenType.CLOSE_BRACKET)) {
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

    //private Map<String, TreeNode> functions
    private TreeNode root;
    private int pos = 0;
}
