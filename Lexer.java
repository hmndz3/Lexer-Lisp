import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Clase que se encarga del análisis léxico de expresiones LISP.
 * Convierte cadenas de texto en tokens que pueden ser procesados por el Parser.
 */
public class Lexer {
    //------------------------------------------------------------------------------------------------------------------------  
    // Conjunto de palabras reservadas del lenguaje LISP
    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>(Arrays.asList(
        "defun", "DEFUN", "setq", "SETQ", "if", "IF", "cond", "COND", "let", "LET", 
        "car", "CAR", "cdr", "CDR", "cons", "CONS", "quote", "QUOTE", "lambda", "LAMBDA", 
        "print", "PRINT", "eval", "EVAL", "apply", "APPLY", "list", "LIST", "append", "APPEND", 
        "null", "NULL", "atom", "ATOM", "+", "-", "*", "/", "=", "<", ">", "<=", ">=", 
        "and", "AND", "or", "OR", "not", "NOT"
    ));

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Convierte una expresión LISP en un array de tokens.
     */
    public static String[] tokenizar(String expresion) {
        String modificada = expresion.replace("(", " ( ").replace(")", " ) ");
        return modificada.trim().split("\\s+");
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Busca palabras reservadas en una expresión LISP.
     * 
     * @param expresion La expresión LISP a analizar
     * @return Una lista con las palabras reservadas encontradas
     */
    public static List<String> buscarPalabrasReservadas(String expresion) {
        List<String> palabrasReservadasEncontradas = new ArrayList<>();
        String[] palabras = expresion.replaceAll("[()]", " ").split("\\s+");

        for (String palabra : palabras) {
            if (!palabra.isEmpty() && esPalabraReservada(palabra)) {
                palabrasReservadasEncontradas.add(palabra.toLowerCase());
            }
        }
        return palabrasReservadasEncontradas;
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Verifica si una palabra es una palabra reservada del lenguaje LISP.
     */
    public static boolean esPalabraReservada(String palabra) {
        return PALABRAS_RESERVADAS.contains(palabra.toLowerCase());
    }
    //------------------------------------------------------------------------------------------------------------------------  
}