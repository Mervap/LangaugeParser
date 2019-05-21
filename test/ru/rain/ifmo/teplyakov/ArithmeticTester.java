package ru.rain.ifmo.teplyakov;

import org.junit.Test;
import ru.rain.ifmo.teplyakov.exception.ParserException;
import ru.rain.ifmo.teplyakov.exception.SyntaxException;

import static org.junit.Assert.*;

public class ArithmeticTester extends BasicTester {

    @Test
    public void add() throws ParserException {
        assertEquals(4, Main.run(helper("(2+2)")));
        assertEquals(6, Main.run(helper("((2+2)+2)")));
        assertEquals(8, Main.run(helper("((2+2)+(2+2))")));
        assertEquals(10, Main.run(helper("(2+(2+(2+(2+2))))")));
    }

    @Test
    public void minus() throws ParserException {
        assertEquals(0, Main.run(helper("(2-2)")));
        assertEquals(2, Main.run(helper("((6-2)-2)")));
        assertEquals(9, Main.run(helper("((10-2)-(5-6))")));
        assertEquals(8, Main.run(helper("(10-(5-(3-(1-1))))")));
    }

    @Test
    public void mul() throws ParserException {
        assertEquals(4, Main.run(helper("(2*2)")));
        assertEquals(24, Main.run(helper("((6*2)*2)")));
        assertEquals(60, Main.run(helper("((1*2)*(5*6))")));
        assertEquals(100000, Main.run(helper("(10*(10*(10*(10*10))))")));
        assertEquals(0, Main.run(helper("((10*10)*(0*(10*9)))")));
    }

    @Test
    public void mod() throws ParserException {
        assertEquals(0, Main.run(helper("(2%2)")));
        assertEquals(1, Main.run(helper("(3/2)")));
        assertEquals(1, Main.run(helper("((14%5)%(7%4))")));
    }

    @Test
    public void allArithmeticOperations() throws ParserException {
        assertEquals(4, Main.run(helper("(2+((3*4)/5))")));
        assertEquals(6, Main.run(helper("((((45*461)/191)+4738)%10)")));
        assertEquals(10, Main.run(helper("(((10/6)*6)+(10%6))")));
    }

    @Test
    public void less() throws ParserException {
        assertEquals(1, Main.run(helper("(1<2)")));
        assertEquals(0, Main.run(helper("(2<1)")));
        assertEquals(1, Main.run(helper("((50-10)<(50-9))")));
        assertEquals(1, Main.run(helper("((2<1)<(1<2))")));
    }

    @Test
    public void greater() throws ParserException {
        assertEquals(0, Main.run(helper("(1>2)")));
        assertEquals(1, Main.run(helper("(2>1)")));
        assertEquals(0, Main.run(helper("((50-10)>(50-9))")));
        assertEquals(1, Main.run(helper("((2>1)>(1>2))")));
    }

    @Test
    public void equals() throws ParserException {
        assertEquals(1, Main.run(helper("(1=1)")));
        assertEquals(1, Main.run(helper("((10-5)=5)")));
        assertEquals(0, Main.run(helper("(1=2)")));
        assertEquals(0, Main.run(helper("(((50+10)-1)=(12*6))")));
    }

    @Test
    public void arithmeticSyntaxError() {
        assertTrue(exceptionHelper("1+2+3"));
        assertTrue(exceptionHelper("(1+2)+3"));
        assertTrue(exceptionHelper("(1 + 2) + 3)"));
        assertTrue(exceptionHelper("(((10/6)*6)+(10%6)"));
        assertTrue(exceptionHelper("(2-+5)"));
        assertTrue(exceptionHelper("(2>>5)"));
//        assertTrue(exceptionHelper("(2/0)"));

        assertFalse(exceptionHelper("(2+-5)")); // It's strange
    }
}
