package ru.rain.ifmo.teplyakov.parser;

import ru.rain.ifmo.teplyakov.exception.FunctionNotFoundException;
import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.SyntaxException;
import ru.rain.ifmo.teplyakov.lexer.Lexer;
import ru.rain.ifmo.teplyakov.lexer.Token;

import java.util.*;

public class Parser {

    public Parser(Lexer lexer) throws ParserException {

        functions = new HashMap<>();
        List<List<Token>> lines = lexer.getLines();

        for (int i = 0; i < lines.size() - 1; ++i) {
            tokens = getLine(lines, i);
            addFunctionDeclaration();
        }

        for (int i = 0; i < lines.size() - 1; ++i) {
            tokens = getLine(lines, i);
            parseFunctionDefinition();
            if (lines.get(i).size() > pos) {
                throw new SyntaxException();
            }
        }

        tokens = getLine(lines, lines.size() - 1);
        root = parseExpression();
        if (lines.get(lines.size() - 1).size() > pos) {
            throw new SyntaxException();
        }
    }

    public List<Token> getLine(List<List<Token>> tokens, int i) {
        pos = 0;
        return tokens.get(i);
    }

    public TreeNode getTree() {
        return root;
    }

    private TreeNode parseExpression() throws ParserException {
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        TreeNode arg;
        Token curToken = getNext();
        if (curToken.getTokenType().equals(Token.TokenType.OPEN_PARENTHESIS)) {
            arg = parseBinaryOperation();
        } else if (curToken.getTokenType().equals(Token.TokenType.OPEN_SQUARE_BRACKET)) {
            arg = parseIfExpression();
        } else if (curToken.getTokenType().equals(Token.TokenType.NUMBER)) {
            arg = new Number(Integer.valueOf(curToken.getToken()));
        } else if (curToken.getTokenType().equals(Token.TokenType.OPERATION)
                && curToken.getToken().equals("-")) {
            TreeNode subArg = parseExpression();
            arg = new UnaryOperation(a -> -a, subArg);
        } else if (curToken.getTokenType().equals(Token.TokenType.IDENTIFIER)) {
            if (getCur().getTokenType().equals(Token.TokenType.OPEN_PARENTHESIS)) {
                ++pos;
                if (!functions.containsKey(curToken.getToken())) {
                    throw new FunctionNotFoundException("");
                }
                arg = new FunctionCall(parseFunctionCall(), functions.get(curToken.getToken()));
            } else {
                arg = new Variable(curToken.getToken());
            }
        } else {
            throw new SyntaxException();
        }

        return arg;
    }

    private Token getCur() throws SyntaxException {
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        return tokens.get(pos);
    }

    private Token getNext() throws SyntaxException {
        if (tokens.size() <= pos) {
            throw new SyntaxException();
        }

        return tokens.get(pos++);
    }

    private boolean nextNotEqual(Token.TokenType tokenType) throws ParserException {
        return !getNext().getTokenType().equals(tokenType);
    }

    private TreeNode parseBinaryOperation() throws ParserException {
        TreeNode left = parseExpression();
        Token opToken = getNext();
        if (!opToken.getTokenType().equals(Token.TokenType.OPERATION)) {
            throw new SyntaxException();
        }
        TreeNode right = parseExpression();

        if (nextNotEqual(Token.TokenType.CLOSE_PARENTHESIS)) {
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

    private TreeNode parseIfExpression() throws ParserException {
        TreeNode condition = parseExpression();

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
            if (nextNotEqual(tmp[cur])) {
                throw new SyntaxException();
            }
        }

        TreeNode trueBranch = parseExpression();
        for (; cur < 6; ++cur) {
            if (nextNotEqual(tmp[cur])) {
                throw new SyntaxException();
            }
        }
        TreeNode falseBranch = parseExpression();
        if (nextNotEqual(tmp[cur])) {
            throw new SyntaxException();
        }

        return new IfExpression(condition, trueBranch, falseBranch);
    }

    private void parseFunctionDefinition() throws ParserException {
        Token functionName = getNext();
        if (nextNotEqual(Token.TokenType.OPEN_PARENTHESIS)) {
            throw new SyntaxException();
        }

        List<String> args = parseParameterList();

        Token tmp = getNext();
        if (!(tmp.getTokenType().equals(Token.TokenType.OPERATION) && tmp.getToken().equals("="))
                || nextNotEqual(Token.TokenType.OPEN_BRACKET)) {
            throw new SyntaxException();
        }

        TreeNode body = parseExpression();

        if (nextNotEqual(Token.TokenType.CLOSE_BRACKET)) {
            throw new SyntaxException();
        }

        functions.get(functionName.getToken()).setFields(args, body);
    }

    private List<String> parseParameterList() throws ParserException {
        Token arg = getNext();
        if (!arg.getTokenType().equals(Token.TokenType.IDENTIFIER)) {
            throw new SyntaxException();
        }

        List<String> result = new ArrayList<>(Collections.singletonList(arg.getToken()));
        while (true) {
            Token tmp = getNext();
            if (tmp.getTokenType().equals(Token.TokenType.CLOSE_PARENTHESIS)) {
                break;
            }

            if (!tmp.getTokenType().equals(Token.TokenType.COMMA)) {
                throw new SyntaxException();
            }

            arg = getNext();
            if (!arg.getTokenType().equals(Token.TokenType.IDENTIFIER)) {
                throw new SyntaxException();
            }
            result.add(arg.getToken());
        }

        return result;
    }

    private List<TreeNode> parseFunctionCall() throws ParserException {
        List<TreeNode> result = new ArrayList<>(Collections.singletonList(parseExpression()));
        while (true) {
            Token curToken = getNext();

            if (curToken.getTokenType().equals(Token.TokenType.CLOSE_PARENTHESIS)) {
                break;
            }

            if (!curToken.getTokenType().equals(Token.TokenType.COMMA)) {
                throw new SyntaxException();
            }

            result.add(parseExpression());
        }

        return result;
    }

    private void addFunctionDeclaration() throws ParserException {
        Token functionName = getNext();
        if (!functionName.getTokenType().equals(Token.TokenType.IDENTIFIER)) {
            throw new SyntaxException();
        }
        functions.put(functionName.getToken(), new FunctionDefinition());
    }

    private Map<String, FunctionDefinition> functions;
    private TreeNode root;
    private int pos = 0;
    private List<Token> tokens;
}
