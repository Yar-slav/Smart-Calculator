import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class DataEntryTest {

    @Test
    void addLine() {
        String input = "3 + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"3", "+", "5"};
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addHelpLine() {
        String input = "/help\n3 + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"3", "+", "5"};
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addHelpLineUnknownVariable() {
        String input = "a + c\n3 + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"3", "+", "5"};
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addLineAddVariable() {
        String input = "a  = 6\na + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"a", "+", "5"};
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addLineAddIncorrectVariable() {
        String input = "a  = 6 = 7\n3 + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"3", "+", "5"};
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addVariables() {
        DataEntry dataEntry = new DataEntry();
        Map<String, BigInteger> variablesExcepted = new HashMap<>();
        variablesExcepted.put("a", BigInteger.valueOf(5));

        String[] variablesAndValue = {"a", "5"};
        dataEntry.addVariables(variablesAndValue);

        assertEquals(variablesExcepted, DataEntry.variables);
    }

    @Test
    void addVariablesOtherVariable() {
        DataEntry dataEntry = new DataEntry();
        Map<String, BigInteger> variablesExcepted = new HashMap<>();
        variablesExcepted.put("a", BigInteger.valueOf(5));
        variablesExcepted.put("b", BigInteger.valueOf(5));
        DataEntry.variables.put("a", BigInteger.valueOf(5));

        String[] variablesAndValue = {"b", "a"};
        dataEntry.addVariables(variablesAndValue);

        assertEquals(variablesExcepted, DataEntry.variables);
    }

    @Test
    void isVariableInvalid() {
        DataEntry dataEntry = new DataEntry();
        String[] variablesAndValue = {"b5", "5"};
        boolean result = dataEntry.isVariablesAndValueInvalid(variablesAndValue);
        assertFalse(result);
    }

    @Test
    void isValueInvalid() {
        DataEntry dataEntry = new DataEntry();
        String[] variablesAndValue = {"b", "5b"};
        boolean result = dataEntry.isVariablesAndValueInvalid(variablesAndValue);
        assertFalse(result);
    }

    @Test
    void isCorrectLineInvalidNumberBracket() {
        DataEntry dataEntry = new DataEntry();

        String[] numbers = {"(", "3", "+", "5", ")", ")"};
        boolean result = dataEntry.isCorrectLine(numbers, "(3 + 5))");
        assertFalse(result);
    }

    @Test
    void isCorrectLineUnknownVariable() {
        DataEntry dataEntry = new DataEntry();
        DataEntry.variables.put("c", BigInteger.valueOf(5));

        String[] numbers = {"c", "+", "d"};
        boolean result = dataEntry.isCorrectLine(numbers, "c + d");
        assertFalse(result);
    }
}