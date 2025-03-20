import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Evaluator {
    @SuppressWarnings("unchecked")
    public static Object evaluate(Object expression, Interpreter interpreter) {
        Map<String, Object> environment = interpreter.getEnvironment();

        // Si es un string, podría ser una variable o valor especial
        if (expression instanceof String) {
            String str = (String) expression;
            
            // Valores especiales
            if ("t".equalsIgnoreCase(str)) {
                return Boolean.TRUE;
            }
            
            // Verificar si es un número
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                // No es un número, verificar si es una variable
                if (environment.containsKey(str)) {
                    return environment.get(str);
                }
                // Si no es una variable, retornar el string como está
                return str;
            }
        }

        if (expression instanceof List<?>) {
            List<?> list = (List<?>) expression;
            if (list.isEmpty()) {
                return list;
            }
            
            if (!(list.get(0) instanceof String)) {
                throw new IllegalArgumentException("Expresión no válida: " + list);
            }
            
            String operator = (String) list.get(0);
            switch (operator) {
                case "+":
                case "-":
                case "*":
                case "/":
                    return interpreter.evaluateArithmetic(operator, list.subList(1, list.size()).toArray());
                case "quote":
                    return list.size() > 1 ? list.get(1) : null;
                case "defun":
                    String functionName = (String) list.get(1);
                    List<?> parameters = (List<?>) list.get(2);
                    Object body = list.get(3);
                    interpreter.defun(functionName, parameters.toArray(), body);
                    return "Function " + functionName + " defined.";
                case "setq":
                    return setq((List<Object>) list, interpreter);
                case "atom":
                    return atom(evaluate(list.get(1), interpreter));
                case "list":
                    return list((List<Object>) list.subList(1, list.size()), interpreter);
                case "equal":
                    return equal(evaluate(list.get(1), interpreter), evaluate(list.get(2), interpreter));
                case "<":
                    return lessThan(evaluate(list.get(1), interpreter), evaluate(list.get(2), interpreter));
                case ">":
                    return greaterThan(evaluate(list.get(1), interpreter), evaluate(list.get(2), interpreter));
                case "cond":
                    return cond((List<Object>) list.subList(1, list.size()), interpreter);
                default:
                    if (environment.containsKey(operator)) {
                        Object func = environment.get(operator);
                        if (func instanceof Function) {
                            Function function = (Function) func;
                            Object[] args = list.subList(1, list.size()).toArray();
                            return function.call(args, environment);
                        } else {
                            return func; // Retornar el valor de la variable
                        }
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

    private static List<Object> list(List<Object> elements, Interpreter interpreter) {
        List<Object> result = new ArrayList<>();
        // Evaluar cada elemento de la lista
        for (Object element : elements) {
            result.add(evaluate(element, interpreter));
        }
        return result;
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
        for (Object condition : conditions) {
            List<Object> condPair = (List<Object>) condition;
            Object test = evaluate(condPair.get(0), interpreter);
            
            // Tratar 't' como verdadero
            if (test instanceof String && "t".equalsIgnoreCase((String)test)) {
                return evaluate(condPair.get(1), interpreter);
            }
            
            // Tratar valores diferentes de NIL como verdaderos
            if ((test instanceof Boolean && (Boolean) test) || 
                (test instanceof String && !"NIL".equalsIgnoreCase((String)test)) ||
                (test instanceof Number && ((Number)test).doubleValue() != 0)) {
                return evaluate(condPair.get(1), interpreter);
            }
        }
        return null;
    }
}