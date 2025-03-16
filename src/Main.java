import java.util.*;

public class Main {
    private static final List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Calculator!");

        while (true) {
            System.out.print("Enter expression (or type 'history' to view past calculations, 'exit' to quit): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using the Calculator!");
                break;
            } else if (input.equalsIgnoreCase("history")) {
                showHistory();
                continue;
            }

            try {
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
                history.add(input + " = " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static double evaluateExpression(String expression) throws Exception {
        expression = expression.replaceAll("\\s+", ""); // Удаляем пробелы

        if (expression.contains("power")) {
            return handleFunction(expression, "power");
        } else if (expression.contains("sqrt")) {
            return handleFunction(expression, "sqrt");
        } else if (expression.contains("abs")) {
            return handleFunction(expression, "abs");
        } else if (expression.contains("round")) {
            return handleFunction(expression, "round");
        }

        // Простые математические операции
        return simpleMath(expression);
    }

    private static double handleFunction(String expression, String function) throws Exception {
        if (!expression.contains("(") || !expression.contains(")")) {
            throw new Exception("Invalid function format");
        }

        String content = expression.substring(expression.indexOf("(") + 1, expression.lastIndexOf(")"));
        double number = Double.parseDouble(content);

        return switch (function) {
            case "power" -> Math.pow(number, 2);
            case "sqrt" -> Math.sqrt(number);
            case "abs" -> Math.abs(number);
            case "round" -> Math.round(number);
            default -> throw new Exception("Unknown function");
        };
    }

    private static double simpleMath(String expression) throws Exception {
        try {
            String[] tokens;
            if (expression.contains("+")) {
                tokens = expression.split("\\+");
                return Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[1]);
            } else if (expression.contains("-")) {
                tokens = expression.split("-");
                return Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1]);
            } else if (expression.contains("*")) {
                tokens = expression.split("\\*");
                return Double.parseDouble(tokens[0]) * Double.parseDouble(tokens[1]);
            } else if (expression.contains("/")) {
                tokens = expression.split("/");
                if (Double.parseDouble(tokens[1]) == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return Double.parseDouble(tokens[0]) / Double.parseDouble(tokens[1]);
            } else if (expression.contains("%")) {
                tokens = expression.split("%");
                return Double.parseDouble(tokens[0]) % Double.parseDouble(tokens[1]);
            }
        } catch (Exception e) {
            throw new Exception("Invalid mathematical expression");
        }
        throw new Exception("Unsupported operation");
    }

    private static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No calculations in history.");
        } else {
            System.out.println("Calculation History:");
            for (String entry : history) {
                System.out.println(entry);
            }
        }
    }
}
