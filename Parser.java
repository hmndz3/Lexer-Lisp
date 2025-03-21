import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se encarga del análisis sintáctico de expresiones LISP.
 * Convierte un array de tokens en una estructura de datos que representa la expresión.
 */
public class Parser {
    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Analiza un array de tokens y construye una estructura de datos que representa la expresión LISP.
     */
    public static Object parse(String[] tokens) {
        int[] pos = {0};
        return parseExpression(tokens, pos);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Método recursivo que analiza una expresión LISP.
     */
    private static Object parseExpression(String[] tokens, int[] pos) {
        if (tokens[pos[0]].equals("(")) {
            pos[0]++;
            List<Object> list = new ArrayList<>();
            while (!tokens[pos[0]].equals(")")) {
                list.add(parseExpression(tokens, pos));
            }
            pos[0]++;
            return list;
        } else {
            return tokens[pos[0]++];
        }
    }
    //------------------------------------------------------------------------------------------------------------------------  
}