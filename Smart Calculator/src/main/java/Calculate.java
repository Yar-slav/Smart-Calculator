import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculate {
    public String[] toInfix() {
        DataEntry dataEntry = new DataEntry();
        String[] numbers = dataEntry.addLine();

        for (int i = 0; i < numbers.length; i++)
            if (DataEntry.variables.containsKey(numbers[i])) {
                numbers[i] = String.valueOf(DataEntry.variables.get(numbers[i]));
            }

        return numbers;
    }

    public boolean isOperator(String operator) {
        return List.of("+", "-", "/", "*", "(", ")")
                .contains(operator);
    }

    public boolean hasLoverPrecedence(String op1, String op2) {
        return precedence(op1) <= precedence(op2);
    }

    public int precedence(String operator) {
        switch (operator) {
            case "-":
            case "+":
                return 1;
            case "*":
            case "/":
                return 2;
            case "(":
                return 0;
            default:
                return 5;
        }
    }

    boolean isNumeric(String str) {
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String infixToPostfix() {
        Stack<String> operators = new Stack<>();
        List<String> postfix = new ArrayList<>();
        for (String current : toInfix()) {
            if (isOperator(current)) {
                if (current.equals("(")) {
                    operators.push(current);
                } else if (current.equals(")")) {
                    while (!operators.peek().equals("(")) {
                        postfix.add(operators.pop());
                    }
                    operators.pop();
                } else {
                    while (!operators.empty() && hasLoverPrecedence(current, operators.peek())) {
                        postfix.add(operators.pop());
                    }
                    operators.push(current);
                }
            } else {
                postfix.add(current);
            }
        }
        while (!operators.empty()) {
            postfix.add(operators.pop());
        }
        return String.join(" ", postfix);
    }

    public BigInteger calculate() {
        Stack<BigInteger> stack = new Stack<>();
        String postfix = infixToPostfix();

        for (String component : postfix.split(" ")) {
            if (isNumeric(component)) {
                stack.push(new BigInteger(component));
            } else {
                BigInteger number1 = stack.pop();
                BigInteger number2 = stack.pop();
                switch (component) {
                    case "+":
                        stack.push(number2.add(number1));
                        break;
                    case "-":
                        stack.push(number2.subtract(number1));
                        break;
                    case "*":
                        stack.push(number2.multiply(number1));
                        break;
                    case "/":
                        if (!number1.equals(new BigInteger("0"))) {
                            stack.push(number2.divide(number1));
                        } else {
                            System.out.println("Сan not be divided by zero");
                            System.exit(0);
                        }
                        break;
                }
            }
        }
        return stack.pop();
    }
}
