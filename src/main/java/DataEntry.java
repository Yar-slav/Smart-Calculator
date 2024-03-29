import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataEntry {
    Scanner scanner = new Scanner(System.in);
    public static Map<String, BigInteger> variables = new HashMap<>();
    Command command = new Command();

    public String[] addLine() {
        String line = scanner.nextLine();
        String[] numbers;
        String[] variablesAndValue;

        if (command.validCommand(line) || line.equals("")) {
            return addLine();
        }

        line = modifyOperatorsInLine(line);
        numbers = line.split(" ");

        // if line is create variable
        if (line.contains("=")) {
            line = Pattern.compile("\\s*").matcher(line).replaceAll("");
            variablesAndValue = line.split("=");
            if (isVariablesAndValueInvalid(variablesAndValue)) {
                addVariables(variablesAndValue);
                return addLine();
            } else {
                System.out.println("Invalid identifier");
                return addLine();
            }
        }

        if (!isCorrectLine(numbers, line)) {
            return addLine();
        }

        return numbers;
    }

    // add variables to map
    public void addVariables(String[] variablesAndValue) {
        String variable = variablesAndValue[0];
        BigInteger value;

        try {
            value = new BigInteger(variablesAndValue[1]);
        } catch (NumberFormatException e) {
            value = new BigInteger(String.valueOf(variables.get(variablesAndValue[1])));
        }
        variables.put(variable, value);
    }

    // checking the correct assignment of a variable
    public boolean isVariablesAndValueInvalid(String[] variablesAndValue) {
        if (variablesAndValue.length > 2) {
            return false;
        }

        String[] split1 = variablesAndValue[0].split("");
        for (String s : split1) {
            if (!s.matches("[a-zA-Z]")) {
                return false;
            }
        }

        String[] split2 = variablesAndValue[1].split("");
        for (String s : split2) {
            if (!s.matches("\\d|[\\-+]") && !variables.containsKey(variablesAndValue[1])) {
                return false;
            }
        }
        return true;
    }

    public boolean isCorrectLine(String[] numbers, String line) {
        int numberBracket = 0;
        for (String number : numbers) {
            if (number.equals("(")) {
                numberBracket++;
            } else if (number.equals(")")) {
                numberBracket--;
            }
        }

        if ((numberBracket != 0) || line.matches(".*(\\*{2,}|/{2,}).*")) {
            System.out.println("Invalid expression");
            return false;
        }

        // checking for the existence of a variable
        for (String number : numbers) {
            if (!variables.containsKey(number)
                    && !number.matches("\\d+|[-+]\\d")
                    && !number.matches("[-+*/()]")) {
                System.out.println("Unknown variable");
                return false;
            }
        }
        return true;
    }

    // modify line if it has empty space on the beginning, space near the brackets or several operators in a row
    private String modifyOperatorsInLine(String line) {
        line = Pattern.compile("^\\s*").matcher(line).replaceAll("");
        line = Pattern.compile("\\*").matcher(line).replaceAll(" * ");
        line = Pattern.compile("/").matcher(line).replaceAll(" / ");
        line = Pattern.compile("=").matcher(line).replaceAll(" = ");
        line = Pattern.compile("\\s+").matcher(line).replaceAll(" ");
        line = Pattern.compile("\\(\\s*").matcher(line).replaceAll("( ");
        line = Pattern.compile("\\s*\\)").matcher(line).replaceAll(" )");
        line = Pattern.compile("\\++").matcher(line).replaceAll("\\+");
        line = Pattern.compile("(-{2})+").matcher(line).replaceAll("\\+");
        line = Pattern.compile("(-\\+|\\+-)").matcher(line).replaceAll("\\-");
        return line;
    }

}
