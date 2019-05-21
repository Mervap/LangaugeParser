package ru.rain.ifmo.teplyakov;

import java.util.Collections;
import java.util.List;

public class BasicTester {

    protected List<String> helper(String s) {
        return Collections.singletonList(s);
    }


    protected boolean exceptionHelper(String s) {
        try {
            Main.run(helper(s));
        } catch (SyntaxException e) {
            return true;
        }

        return false;
    }
}
