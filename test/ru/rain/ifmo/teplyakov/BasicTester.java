package ru.rain.ifmo.teplyakov;

import ru.rain.ifmo.teplyakov.exception.LanguageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicTester {

    protected int helper(String... s) throws LanguageException {
        List<String> result = new ArrayList<>();
        Collections.addAll(result, s);
        return Interpreter.run(result);
    }


    protected boolean exceptionHelper(String... s) {
        try {
            helper(s);
        } catch (LanguageException e) {
            return true;
        }

        return false;
    }
}
