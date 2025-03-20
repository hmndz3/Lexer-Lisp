import java.util.HashMap;
import java.util.Map;

public class Interpreter {
    private Map<String, Object> environment = new HashMap<>();

    public Object evaluate(String expression) {
        if (!Utils.cantParentesis(expression)) {
            throw new IllegalArgumentException("No están balanceados los paréntesis, revisar expresión ingresada");
        }

        String[] tokens = Lexer.tokenizar(expression);
        Object parsedExpression = Parser.parse(tokens);
        return Evaluator.evaluate(parsedExpression, environment);
    }

    // Métodos para operaciones aritméticas, QUOTE, y DEFUN
   /*  public static Object evaluateArithmetic(String operator, Object[] operands) {
        // Implementación de operaciones aritméticas (+, -, *, /)
    }

    public static Object quote(Object expression) {
        // Implementación de QUOTE
    }

    public static void defun(String functionName, Object[] parameters, Object body) {
        // Implementación de DEFUN
    }*/
}