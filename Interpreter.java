import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal que interpreta y evalúa expresiones LISP.
 * Mantiene un entorno con las variables y funciones definidas.
 */
public class Interpreter {
    //------------------------------------------------------------------------------------------------------------------------  
    // Atributos de la clase Interpreter
    private Map<String, Object> environment = new HashMap<>();

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Evalúa una expresión LISP en formato de cadena.
     * @return El resultado de la evaluación
     * @throws IllegalArgumentException Si los paréntesis no están balanceados
     */
    public Object evaluate(String expression) {
        if (!Utils.cantParentesis(expression)) {
            throw new IllegalArgumentException("No están balanceados los paréntesis, revisar expresión ingresada");
        }

        String[] tokens = Lexer.tokenizar(expression);
        Object parsedExpression = Parser.parse(tokens);
        return Evaluator.evaluate(parsedExpression, this);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Método alternativo para interpretar expresiones LISP ya parseadas.
     * 
     * La expresión LISP parseada a interpretar
     * @return El resultado de la interpretación
     */
    public Object interpretar(Object expresion) {
        if (expresion instanceof List) {
            List<?> lista = (List<?>) expresion;
            if (lista.isEmpty()) return lista;

            String getOperador = lista.get(0).toString().toUpperCase(); 

            if ("SETQ".equals(getOperador)) {
                String variable = lista.get(1).toString();
                Object valor = interpretar(lista.get(2));
                environment.put(variable, valor);
                return valor;
            }

            if ("COND".equals(getOperador)) {
                for (int i = 1; i < lista.size(); i++) {
                    List<?> par = (List<?>) lista.get(i);
                    if (par.size() != 2) {
                        throw new RuntimeException("El par COND tiene que contener 2 elementos.");
                    }
                    Object condicionalPrincipal = interpretar(par.get(0));
                    if (!"NIL".equals(condicionalPrincipal.toString())) {
                        return interpretar(par.get(1));
                    }
                }
                return "NIL/falso";
            }

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
                throw new RuntimeException("No se reconoció el operador ingresado");
            }
        }

        if (expresion instanceof String && environment.containsKey(expresion)) {
            return environment.get(expresion);
        }

        return expresion;
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Método auxiliar para comparar números según el operador proporcionado.
     */
    private String relacionarNums(String operador, Object n1, Object n2) {
        if (!(n1 instanceof Number) || !(n2 instanceof Number)) {
            throw new RuntimeException("Valores incorrectos, deben ser números para compararse.");
        }

        double primerNumero = ((Number) n1).doubleValue();
        double segundoNumero = ((Number) n2).doubleValue();

        return operador.equals("<") ? (primerNumero < segundoNumero ? "T" : "NIL") 
        : (primerNumero > segundoNumero ? "T" : "NIL");
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Evalúa operaciones aritméticas con los operandos proporcionados.
     */
    public Object evaluateArithmetic(String operator, Object[] operands) {
        double resultado = 0;
        
        // Primero evaluar cada operando
        Object[] evaluatedOperands = new Object[operands.length];
        for (int i = 0; i < operands.length; i++) {
            Object evaluatedOperand = Evaluator.evaluate(operands[i], this);
            evaluatedOperands[i] = evaluatedOperand;
        }
    
        switch (operator) {
            case "+":
                resultado = 0;
                for (Object operand : evaluatedOperands) {
                    resultado += Double.parseDouble(operand.toString());
                }
                break;
            case "-":
                resultado = Double.parseDouble(evaluatedOperands[0].toString());
                for (int i = 1; i < evaluatedOperands.length; i++) {
                    resultado -= Double.parseDouble(evaluatedOperands[i].toString());
                }
                break;
            case "*":
                resultado = 1;
                for (Object operand : evaluatedOperands) {
                    resultado *= Double.parseDouble(operand.toString());
                }
                break;
            case "/":
                resultado = Double.parseDouble(evaluatedOperands[0].toString());
                for (int i = 1; i < evaluatedOperands.length; i++) {
                    double divisor = Double.parseDouble(evaluatedOperands[i].toString());
                    if (divisor == 0) {
                        throw new ArithmeticException("División por cero");
                    }
                    resultado /= divisor;
                }
                break;
            default:
                throw new UnsupportedOperationException("Operador no soportado: " + operator);
        }
        
        // Si el resultado es un entero, retornarlo como tal
        if (resultado == Math.floor(resultado)) {
            return (int) resultado;
        }
        return resultado;
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Implementa la funcionalidad de quote para evitar la evaluación de una expresión.
     */
    public Object quote(Object expression) {
        return expression;
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Define una nueva función con el nombre, parámetros y cuerpo especificados.
     * 
     */
    public void defun(String functionName, Object[] parameters, Object body) {
        Function function = new Function(parameters, body, this);
        environment.put(functionName, function);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Obtiene el entorno actual del intérprete.
     * @return El mapa que representa el entorno con las variables y funciones definidas
     */
    public Map<String, Object> getEnvironment() {
        return environment;
    }
    //------------------------------------------------------------------------------------------------------------------------  
}