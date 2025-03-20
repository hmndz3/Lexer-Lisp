import java.util.List;
import java.util.Map;

public class Evaluator {
    @SuppressWarnings("unchecked")
    public static Object evaluate(Object expression, Interpreter interpreter) {
        Map<String, Object> environment = interpreter.getEnvironment();

        if (expression instanceof List<?>) {
            List<?> list = (List<?>) expression;
            if (list.isEmpty() || !(list.get(0) instanceof String)) {
                throw new IllegalArgumentException("Expresión no válida");
            }
            String operator = (String) list.get(0);
            switch (operator) {
                case "+":
                case "-":
                case "*":
                case "/":
                    return interpreter.evaluateArithmetic(operator, list.subList(1, list.size()).toArray());
                case "quote":
                    return interpreter.quote(list.get(1));
                case "defun":
                    String functionName = (String) list.get(1);
                    List<?> parameters = (List<?>) list.get(2);
                    Object body = list.get(3);
                    interpreter.defun(functionName, parameters.toArray(), body);
                    return "Function " + functionName + " defined.";
                case "setq":
                    return setq((List<Object>) list, interpreter);
                case "atom":
                    return atom(list.get(1));
                case "list":
                    return list((List<Object>) list.subList(1, list.size()));
                case "equal":
                    return equal(list.get(1), list.get(2));
                case "<":
                    return lessThan(list.get(1), list.get(2));
                case ">":
                    return greaterThan(list.get(1), list.get(2));
                case "cond":
                    return cond((List<Object>) list.subList(1, list.size()), interpreter);
                default:
                    if (environment.containsKey(operator)) {
                        Function function = (Function) environment.get(operator);
                        Object[] args = list.subList(1, list.size()).toArray();
                        return function.call(args, environment);
                    } else {
                        throw new UnsupportedOperationException("Operador no soportado: " + operator);
                    }
            }
        } else {
            return expression;
        }
    }

    private static Object setq(List<Object> list, Interpreter interpreter) {
        Map<String, Object> environment = interpreter.getEnvironment();
        String variable = (String) list.get(1);
        Object value = evaluate(list.get(2), interpreter);
        environment.put(variable, value);
        return value;
    }

    private static boolean atom(Object expression) {
        return !(expression instanceof List);
    }

    private static List<Object> list(List<Object> elements) {
        return elements;
    }

    private static boolean equal(Object a, Object b) {
        return a.equals(b);
    }

    private static boolean lessThan(Object a, Object b) {
        return Double.parseDouble(a.toString()) < Double.parseDouble(b.toString());
    }

    private static boolean greaterThan(Object a, Object b) {
        return Double.parseDouble(a.toString()) > Double.parseDouble(b.toString());
    }

    @SuppressWarnings("unchecked")
    private static Object cond(List<Object> conditions, Interpreter interpreter) {
        Map<String, Object> environment = interpreter.getEnvironment();
        for (Object condition : conditions) {
            List<Object> condPair = (List<Object>) condition;
            Object test = evaluate(condPair.get(0), interpreter);
            if (test instanceof Boolean && (Boolean) test) {
                return evaluate(condPair.get(1), interpreter);
            }
        }
        return null;
    }
}