import static org.junit.jupiter.api.Assertions.*;

import com.ginsberg.junit.exit.ExpectSystemExit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class CalculateTest {

    @Test
    void toInfix_ChangeLineToInfix_True() {
        String input = "c = 5\n(3 + c) / 2\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Calculate calculate = new Calculate();

        String[] lineExcepted = {"(", "3", "+", "5", ")", "/", "2"};
        String[] lineActual = calculate.toInfix();
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void precedence_characterWithoutPriority() {
        Calculate calculate = new Calculate();
        int result = calculate.precedence(")");
        assertEquals(5, result);
    }

    @Test
    void isNumeric_checkNumberIsNumeric_True() {
        Calculate calculate = new Calculate();
        boolean result = calculate.isNumeric("5");
        assertTrue(result);
    }

    @Test
    void isNumeric_checkNumberIsNumeric_False() {
        Calculate calculate = new Calculate();
        boolean result = calculate.isNumeric("+");
        assertFalse(result);
    }

    @Test
    void infixToPostfix_transformationFromInfixToPostfix() {
        String input = "c = 5\n80 / (3 + c) / (3 - 1)\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Calculate calculate = new Calculate();

        String lineExcepted = "80 3 5 + / 3 1 - /";
        String lineActual = calculate.infixToPostfix();
        assertEquals(lineExcepted, lineActual);
    }

    @Test
    void calculate_wrightCalculating_True() {
        String input = "c = 5\n80 / (3 + c) * (3 - 1)\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Calculate calculate = new Calculate();

        BigInteger lineActual = calculate.calculate();
        assertEquals(BigInteger.valueOf(20), lineActual);
    }

    @Test
    @ExpectSystemExit
    void calculate_calculateDividedZeroExitProgram() {
        String input = "2 / 0\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Calculate calculate = new Calculate();

        calculate.calculate();
    }

}