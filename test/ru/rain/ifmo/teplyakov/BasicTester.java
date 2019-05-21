package ru.rain.ifmo.teplyakov;

import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.SyntaxException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicTester {

    protected List<String> helper(String ... s) {
        List<String> result = new ArrayList<>();
        Collections.addAll(result, s);
        return result;
    }


    protected boolean exceptionHelper(String ... s) {
        try {
            Main.run(helper(s));
        } catch (ParserException e) {
            return true;
        }

        return false;
    }
}
