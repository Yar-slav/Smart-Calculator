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
    void addLine_ExceptedCorrectInput() {
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
    void addLine_ExceptedCallHelpAndContinueWork() {
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
    void addLine_ExceptedUnknownVariableAndContinueWork() {
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
    void addLine_ExceptedAddVariableAndContinueWork() {
        String input = "a  = 6\na + 5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        DataEntry dataEntry = new DataEntry();
        Map<String, BigInteger> map = new HashMap<>();
        map.put("a", BigInteger.valueOf(6));

        String[] lineActual = dataEntry.addLine();
        String[] lineExcepted = {"a", "+", "5"};
        assertEquals(map, DataEntry.variables);
        assertArrayEquals(lineExcepted, lineActual);
    }

    @Test
    void addLine_AddIncorrectVariableAndContinueWork() {
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
    void addVariables_True() {
        DataEntry dataEntry = new DataEntry();
        Map<String, BigInteger> variablesExcepted = new HashMap<>();
        variablesExcepted.put("a", BigInteger.valueOf(5));

        String[] variablesAndValue = {"a", "5"};
        dataEntry.addVariables(variablesAndValue);

        assertEquals(variablesExcepted, DataEntry.variables);
    }

    @Test
    void addVariables_AssignAVariableTheValueOfAnotherVariable() {
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
    void isVariablesAndValueValid_IncorrectVariable_False() {
        DataEntry dataEntry = new DataEntry();
        String[] variablesAndValue = {"b5", "5"};
        boolean result = dataEntry.isVariablesAndValueValid(variablesAndValue);
        assertFalse(result);
    }

    @Test
    void isVariablesAndValueValid_IncorrectValue_False() {
        DataEntry dataEntry = new DataEntry();
        String[] variablesAndValue = {"b", "5b"};
        boolean result = dataEntry.isVariablesAndValueValid(variablesAndValue);
        assertFalse(result);
    }

    @Test
    void isCorrectLine_InappropriateNumberOfBrackets_False() {
        DataEntry dataEntry = new DataEntry();

        String[] numbers = {"(", "3", "+", "5", ")", ")"};
        boolean result = dataEntry.isCorrectLine(numbers, "(3 + 5))");
        assertFalse(result);
    }

    @Test
    void isCorrectLine_checkExistVariable_UnknownVariable() {
        DataEntry dataEntry = new DataEntry();
        DataEntry.variables.put("c", BigInteger.valueOf(5));

        String[] numbers = {"c", "+", "d"};
        boolean result = dataEntry.isCorrectLine(numbers, "c + d");
        assertFalse(result);
    }
}