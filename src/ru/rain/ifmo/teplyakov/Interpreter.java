package ru.rain.ifmo.teplyakov;

import ru.rain.ifmo.teplyakov.exception.LanguageException;
import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.RuntimeErrorException;
import ru.rain.ifmo.teplyakov.lexer.Lexer;
import ru.rain.ifmo.teplyakov.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Interpreter {

    public static Integer run(List<String> lines) throws ParserException, RuntimeErrorException {
        Lexer lexer = new Lexer();
        for (String s : lines) {
            lexer.addTokens(s);
        }

        Parser parser = new Parser(lexer);
        try {
            return parser.getTree().evaluate(Collections.emptyMap());
        } catch (RuntimeErrorException e) {
            e.setLine(lines.size());
            throw e;
        }
    }

    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            lines.add(in.nextLine());
        }

        try {
            System.out.println(run(lines));
        } catch (LanguageException e) {
            System.out.println(e.getMessage());
        }
    }
}
