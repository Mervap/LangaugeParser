package ru.rain.ifmo.teplyakov;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NoArithmeticTester {

    List<String> helper(String s) {
        return Collections.singletonList(s);
    }

    @Test
    public void add()  throws SyntaxException  {
        assertEquals(4, Main.run(helper("(2+2)")));
        assertEquals(6, Main.run(helper("((2+2)+2)")));
        assertEquals(8, Main.run(helper("((2+2)+(2+2))")));
    }
}
