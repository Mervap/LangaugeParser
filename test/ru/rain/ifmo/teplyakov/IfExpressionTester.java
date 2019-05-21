package ru.rain.ifmo.teplyakov;

import org.junit.Test;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IfExpressionTester extends BasicTester {

    @Test
    public void testCorrect() throws ParserException {
        assertEquals(-100, helper("[((10+20)>(20+10))]?{100}:{-100}"));
        assertEquals(-100, helper("[((10+20)<(20+10))]?{100}:{-100}"));
        assertEquals(100, helper("[((10+20)=(20+10))]?{100}:{-100}"));
        assertEquals(100, helper("[((10+20)=(20+10))]?{100}:{-100}"));
        assertEquals(42, helper("[((42%2)=0)]?{42}:{0}"));
        assertEquals(0, helper("[((43%2)=0)]?{42}:{0}"));
        assertEquals(128, helper("[5]?{(256/2)}:{(256%2)}"));
        assertEquals(0, helper("[0]?{(256/2)}:{(256%2)}"));
        assertEquals(128, helper("[-5]?{(256/2)}:{(256%2)}"));
    }

    @Test
    public void arithmeticSyntaxError() {
        assertTrue(exceptionHelper("[]?{1}:{0}"));
        assertTrue(exceptionHelper("[5]{1}:{0}"));
        assertTrue(exceptionHelper("[5]?{1}{0}"));
        assertTrue(exceptionHelper("[5){1}:{0}"));
        assertTrue(exceptionHelper("(5]{1}:{0}"));
        assertTrue(exceptionHelper("(5+6)]{1}:{0}"));
        assertTrue(exceptionHelper("[(5+6)]?(1):{0}"));
        assertTrue(exceptionHelper("[(5+6)]?{1}:(0)"));
        assertTrue(exceptionHelper("[(5+6)]:{1}?(0)"));
    }
}
