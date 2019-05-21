package ru.rain.ifmo.teplyakov;

import org.junit.Test;
import ru.rain.ifmo.teplyakov.exception.LanguageException;

import static org.junit.Assert.*;

public class FunctionTester extends BasicTester {

    @Test
    public void testCorrect() throws LanguageException {
        assertEquals(5, helper("id(x)={x}", "id(5)"));
        assertEquals(20, helper("f(x,y)={(x+y)}", "f(5,15)"));
        assertEquals(1024, helper("pow(x,y)={[(y>1)]?{(x*pow(x,(y-1)))}:{x}}", "pow(2,10)"));
        assertEquals(64, helper("UPPER_CASE(X)={(X*X)}", "UPPER_CASE(8)"));

        assertEquals(60, helper("g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(10)"));
        assertEquals(1, helper("g(x)={(f(x)+f((x/2)))}", "f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}", "g(g(1))"));
        assertEquals(120, helper("f(x)={[(x>1)]?{(f((x-1))*x)}:{1}}", "f(5)"));
        assertEquals(40320, helper("f(x)={[(x>1)]?{(f((x-1))*x)}:{1}}", "f(((2+2)*2))"));

        assertEquals(-10, helper("id(a)={a}", "nId(a)={-a}", "main(x,y)={(id(x)+nId(y))}", "main(50,60)"));
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

    @Test
    public void runtimeError() {
        assertTrue(exceptionHelper("g(a,b)={(a/b)}", "g(10,0)"));
        assertTrue(exceptionHelper("g(a,b)={(a/[(a<b)]?{0}:{20})}", "g(0,10)"));
        assertTrue(exceptionHelper("zero(a)={0}", "notZero(a)={1}", "(notZero(0)/zero(1))"));
        assertTrue(exceptionHelper("zero(a)={0}", "notZero(a)={1}",
                "main(x,y)={(notZero(x)/zero(y))}", "main(0,1)"));

        assertFalse(exceptionHelper("zero(a)={0}", "notZero(a)={1}",
                "main(x,y)={(zero(x)/notZero(y))}", "main(1,0)"));
    }
}
