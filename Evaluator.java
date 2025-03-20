import java.util.List;
import java.util.Map;

public class Evaluator {
    public static Object evaluate(Object expression, Map<String, Object> environment) {
        if (expression instanceof List) {
            List<Object> list = (List<Object>) expression;
            String operator = (String) list.get(0);
            switch (operator) {
                case "+":
                case "-":
                case "*":
                case "/":
                    return Interpreter.evaluateArithmetic(operator, list.subList(1, list.size()).toArray());
                case "quote":
                    return Interpreter.quote(list.get(1));
                case "defun":
                    Interpreter.defun((String) list.get(1), (Object[]) list.get(2), list.get(3));
                    return null;
                // Otros casos para SETQ, ATOM, LIST, EQUAL, <, >, COND
                default:
                    throw new UnsupportedOperationException("Operador no soportado: " + operator);
            }
        } else {
            return expression;
        }
    }
}