package ru.rain.ifmo.teplyakov;

import org.junit.Test;
import ru.rain.ifmo.teplyakov.exception.ParserException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunctionTester extends BasicTester {

    @Test
    public void testCorrect() throws ParserException {
        assertEquals(5, helper("id(x)={x}", "id(5)"));
        assertEquals(20, helper("f(x,y)={(x+y)}", "f(5,15)"));
        assertEquals(1024, helper("pow(x,y)={[(y>1)]?{(x*pow(x,(y-1)))}:{x}}", "pow(2,10)"));

        assertEquals(60, helper("g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(10)"));
        assertEquals(1, helper("g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(g(1))"));
        assertEquals(120, helper("f(x)={[(x>1)]?{(f((x-1))*x)}:{1}}", "f(5)"));
        assertEquals(40320, helper("f(x)={[(x>1)]?{(f((x-1))*x)}:{1}}", "f(((2+2)*2))"));
    }

    @Test
    public void testException() {
        assertTrue(exceptionHelper("g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(10))"));
        assertTrue(exceptionHelper("g(x)={(f(x)+f((y/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(10)"));
        assertTrue(exceptionHelper("g(x)={(f(x)+f((y/2)))}", "f(x)={[(x>1)]?{(h((x-1))+h((x-2)))}:{x}}", "g(10)"));
        assertTrue(exceptionHelper("f(10)"));
        assertTrue(exceptionHelper("f(x,y)={(x+y)}", "f(10)"));
        assertTrue(exceptionHelper("f()={10}", "f()"));
        assertTrue(exceptionHelper("f(x)={(x+y)}", "g(x,y)={f(x)}", "g(5,10)"));
    }
}
