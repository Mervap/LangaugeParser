package ru.rain.ifmo.teplyakov;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ArithmeticTester {

    private List<String> helper(String s) {
        return Collections.singletonList(s);
    }

    @Test
    public void add() throws SyntaxException {
        assertEquals(4, Main.run(helper("(2+2)")));
        assertEquals(6, Main.run(helper("((2+2)+2)")));
        assertEquals(8, Main.run(helper("((2+2)+(2+2))")));
        assertEquals(10, Main.run(helper("(2+(2+(2+(2+2))))")));
    }

    @Test
    public void minus() throws SyntaxException {
        assertEquals(0, Main.run(helper("(2-2)")));
        assertEquals(2, Main.run(helper("((6-2)-2)")));
        assertEquals(9, Main.run(helper("((10-2)-(5-6))")));
        assertEquals(8, Main.run(helper("(10-(5-(3-(1-1))))")));
    }

    @Test
    public void mul() throws SyntaxException {
        assertEquals(4, Main.run(helper("(2*2)")));
        assertEquals(24, Main.run(helper("((6*2)*2)")));
        assertEquals(60, Main.run(helper("((1*2)*(5*6))")));
        assertEquals(100000, Main.run(helper("(10*(10*(10*(10*10))))")));
        assertEquals(0, Main.run(helper("((10*10)*(0*(10*9)))")));
    }

    @Test
    public void mod() throws SyntaxException {
        assertEquals(0, Main.run(helper("(2%2)")));
        assertEquals(1, Main.run(helper("(3/2)")));
        assertEquals(1, Main.run(helper("((14%5)%(7%4))")));
    }

    @Test
    public void allOperations() throws SyntaxException {
        assertEquals(4, Main.run(helper("(2+((3*4)/5))")));
        assertEquals(6, Main.run(helper("((((45*461)/191)+4738)%10)")));
        assertEquals(10, Main.run(helper("(((10/6)*6)+(10%6))")));
    }

    private boolean exceptionHelper(String s) {
        try {
            Main.run(helper(s));
        } catch (SyntaxException e) {
            return true;
        }

        return false;
    }

    @Test
    public void arithmeticSyntaxError() {
        assertTrue(exceptionHelper("1+2+3"));
        assertTrue(exceptionHelper("(1+2)+3"));
        assertTrue(exceptionHelper("(1 + 2) + 3)"));
        assertTrue(exceptionHelper("(((10/6)*6)+(10%6)"));
        assertTrue(exceptionHelper("(2-+5)"));
        assertTrue(exceptionHelper("(2>>5)"));

        assertFalse(exceptionHelper("(2+-5)")); // It's strange
    }
}
