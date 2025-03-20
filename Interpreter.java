import java.util.HashMap;
import java.util.List;
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

    // Puntos 4, 5 y 6
    public Object interpretar(Object expresion) {
        if (expresion instanceof List) {
            List<?> lista = (List<?>) expresion;
            if (lista.isEmpty()) return lista;

            //obtener el primer elemento de la lista, ya sea setq, cond o cualquier otra.
            String getOperador = lista.get(0).toString().toLowerCase(); 


            // SETQ
            if ("SETQ".equals(getOperador)) {
                String variable = lista.get(1).toString();
                Object valor = interpretar(lista.get(2));
                environment.put(variable, valor);
                return valor;
            }

            // COND 
            if ("COND".equals(getOperador)) {
                for (int i = 1; i < lista.size(); i++) {
                    List<?> par = (List<?>) lista.get(i);
                    if (par.size() != 2) {
                        throw new RuntimeException("el par COND tiene que contener 2 elementos.");
                    }
                    Object condicionalPrincipal = interpretar(par.get(0));
                    if (!"NIL".equals(condicionalPrincipal.toString())) {
                        return interpretar(par.get(1));
                    }
                }
                return "NIL/falso";  // Si ninguna condición se cumple
            }

            // Predicados ATOM, LIST, EQUAL, <, >
            // se evaluan primero paar evitar repeticion de codigo
            Object op1 = lista.size() > 1 ? interpretar(lista.get(1)) : null;
            Object op2 = lista.size() > 2 ? interpretar(lista.get(2)) : null;

            if ("ATOM".equals(getOperador)) {
                return (op1 != null && !(op1 instanceof List)) ? "T" : "NIL";
            } else if ("LIST".equals(getOperador)) {
                return (op1 instanceof List) ? "T" : "NIL";
            } else if ("EQUAL".equals(getOperador)) {
                return (op1 != null && op1.equals(op2)) ? "T" : "NIL";
            } else if ("<".equals(getOperador) || ">".equals(getOperador)) {
                return relacionarNums(getOperador, op1, op2);
            } else {
                throw new RuntimeException("No se reconocio el operador ingresado" );
            }

        }

        // Si es una variable, deuelve su valor
        if (expresion instanceof String && environment.containsKey(expresion)) {
            return environment.get(expresion);
        }

        return expresion;
    }

    // relaciona numeros y verifica que sean numeros antes de comparar
    private String relacionarNums(String operador, Object n1, Object n2) {
        if (!(n1 instanceof Number) || !(n2 instanceof Number)) {
            throw new RuntimeException("valores incorrectos, deben ser numeros para cmopararse.");
        }
    
        // los convierte a double para compararlos
        double primerNumero = ((Number) n1).doubleValue();
        double segundoNumero = ((Number) n2).doubleValue();
    
        //dependiendo del operador realiza la comparacion
        return operador.equals("<") ? (primerNumero < segundoNumero ? "T" : "NIL") 
        : (primerNumero > segundoNumero ? "T" : "NIL");
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