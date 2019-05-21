package ru.rain.ifmo.teplyakov;

import org.junit.Test;
import ru.rain.ifmo.teplyakov.exception.LanguageException;

import static org.junit.Assert.*;

public class ArithmeticTester extends BasicTester {

    @Test
    public void add() throws LanguageException {
        assertEquals(4, helper("(2+2)"));
        assertEquals(6, helper("((2+2)+2)"));
        assertEquals(8, helper("((2+2)+(2+2))"));
        assertEquals(10, helper("(2+(2+(2+(2+2))))"));
    }

    @Test
    public void minus() throws LanguageException {
        assertEquals(0, helper("(2-2)"));
        assertEquals(2, helper("((6-2)-2)"));
        assertEquals(9, helper("((10-2)-(5-6))"));
        assertEquals(8, helper("(10-(5-(3-(1-1))))"));
    }

    @Test
    public void mul() throws LanguageException {
        assertEquals(4, helper("(2*2)"));
        assertEquals(24, helper("((6*2)*2)"));
        assertEquals(60, helper("((1*2)*(5*6))"));
        assertEquals(100000, helper("(10*(10*(10*(10*10))))"));
        assertEquals(0, helper("((10*10)*(0*(10*9)))"));
    }

    @Test
    public void mod() throws LanguageException {
        assertEquals(0, helper("(2%2)"));
        assertEquals(1, helper("(3/2)"));
        assertEquals(1, helper("((14%5)%(7%4))"));
    }

    @Test
    public void allArithmeticOperations() throws LanguageException {
        assertEquals(4, helper("(2+((3*4)/5))"));
        assertEquals(6, helper("((((45*461)/191)+4738)%10)"));
        assertEquals(10, helper("(((10/6)*6)+(10%6))"));
    }

    @Test
    public void less() throws LanguageException {
        assertEquals(1, helper("(1<2)"));
        assertEquals(0, helper("(2<1)"));
        assertEquals(1, helper("((50-10)<(50-9))"));
        assertEquals(1, helper("((2<1)<(1<2))"));
    }

    @Test
    public void greater() throws LanguageException {
        assertEquals(0, helper("(1>2)"));
        assertEquals(1, helper("(2>1)"));
        assertEquals(0, helper("((50-10)>(50-9))"));
        assertEquals(1, helper("((2>1)>(1>2))"));
    }

    @Test
    public void equals() throws LanguageException {
        assertEquals(1, helper("(1=1)"));
        assertEquals(1, helper("((10-5)=5)"));
        assertEquals(0, helper("(1=2)"));
        assertEquals(0, helper("(((50+10)-1)=(12*6))"));
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

    @Test
    public void runtimeError() {
        assertTrue(exceptionHelper("(2/0)"));
        assertTrue(exceptionHelper("((20+50)%((15-40)*(25-(10+15))))"));
    }
}
