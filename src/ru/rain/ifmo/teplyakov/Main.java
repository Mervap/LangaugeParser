package ru.rain.ifmo.teplyakov;

import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.SyntaxException;
import ru.rain.ifmo.teplyakov.lexer.Lexer;
import ru.rain.ifmo.teplyakov.lexer.Token;
import ru.rain.ifmo.teplyakov.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Object run(List<String> lines) throws ParserException {
        Lexer lexer = new Lexer();
        for (String s : lines) {
            lexer.addTokens(s);
        }

        Parser parser = new Parser(lexer);
        return parser.getTree().evaluate(Collections.emptyMap());
    }

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            lines.add(in.nextLine());
        }

        try {
            run(lines);
        } catch (ParserException e) {
            System.out.println(e.getCause());
        }
    }
}
