package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class DataEntry {
    public static Map<String, BigInteger> variablesMap = new HashMap<>();


    public static String[] addLine() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] numbers;
        String[] variablesAndValue;

        if(Command.validCommand(line)) {
            return addLine();
        }
        if (line.equals("")){
            return addLine();
        }

        line = modifyOperatorsInLine(line);
        numbers = line.split(" ");

        // if line is create variable
        if(line.contains("=")) {
            line = Pattern.compile("\\s*").matcher(line).replaceAll("");
            variablesAndValue = line.split("=");
            if(isVariablesAndValueInvalid(variablesAndValue)) {
                addVariables(variablesAndValue);
                return addLine();
            } else {
                System.out.println("Invalid identifier");
            }
        }

        if(!isCorrectLine(numbers, line)) {
            return addLine();
        }

        return numbers;
    }

    // add variables to map
    public static Map<String, BigInteger> addVariables(String[] variablesAndValue) {
        String  variable = variablesAndValue[0];
        BigInteger value = null;

        try{
            value = new BigInteger(variablesAndValue[1]);
        } catch (NumberFormatException e) {
            if(variablesMap.containsKey(variablesAndValue[1])) {
                value = new BigInteger(String.valueOf(variablesMap.get(variablesAndValue[1])));
            } else {
                e.printStackTrace();
                System.out.println("Unknown variable");
            }
        }
        variablesMap.put(variable, value);

        return variablesMap;
    }

    // checking the correct assignment of a variable
    public static boolean isVariablesAndValueInvalid(String[] variablesAndValue) {
        if(variablesAndValue.length > 2) {
            return false;
        }

        String[] split1 = variablesAndValue[0].split("");
        for(String s : split1) {
            if(!s.matches("[a-zA-Z]")){
                return false;
            }
        }

        String[] split2 = variablesAndValue[1].split("");
        for(String s : split2) {
            if(!s.matches("\\d|[\\-\\+]") && !variablesMap.containsKey(variablesAndValue[1])){
                return false;
            }
        }
        return true;
    }

    public static boolean isCorrectLine(String[] numbers, String line) {
        int numberBracket = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numbers[i].equals("(")) {
                numberBracket++;
            } else if (numbers[i].equals(")")) {
                numberBracket--;
            }
        }

        // check correct brackets
        if(numberBracket != 0) {
            System.out.println("Invalid expression");
            return false;
        } else if (line.matches(".*(\\*{2,}|\\/{2,}).*")) {
            System.out.println("Invalid expression");
            return false;
        }

        // checking for the existence of a variable
        for (int i = 0; i < numbers.length; i++) {
            if(!variablesMap.containsKey(numbers[i]) && !numbers[i].matches("\\d+|[\\-\\+]\\d") // можлива помилка
                    && !numbers[i].matches("\\-|\\+|\\*|\\/|\\(|\\)")) {
                System.out.println("Unknown variable");
                return false;
            }

        }
        return true;
    }

    // modify line if it has empty space on the beginning, space near the brackets or several operators in a row
    private static String modifyOperatorsInLine(String line) {

        line = Pattern.compile("^\\s*").matcher(line).replaceAll("");
        line = Pattern.compile("\\s+").matcher(line).replaceAll(" ");
        line = Pattern.compile("\\(\\s*").matcher(line).replaceAll("( ");
        line = Pattern.compile("\\s*\\)").matcher(line).replaceAll(" )");
        line = Pattern.compile("\\++").matcher(line).replaceAll("\\+");
        line = Pattern.compile("(\\-{2})+").matcher(line).replaceAll("\\+");
        line = Pattern.compile("(\\-\\+|\\+\\-)").matcher(line).replaceAll("\\-"); // don't work if entry -+-

        return line;
    }




}
