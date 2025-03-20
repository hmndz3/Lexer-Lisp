import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lexer {

    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>(Arrays.asList(
        "defun", "DEFUN", "setq", "SETQ", "if", "IF", "cond", "COND", "let", "LET", 
        "car", "CAR", "cdr", "CDR", "cons", "CONS", "quote", "QUOTE", "lambda", "LAMBDA", 
        "print", "PRINT", "eval", "EVAL", "apply", "APPLY", "list", "LIST", "append", "APPEND", 
        "null", "NULL", "atom", "ATOM", "+", "-", "*", "/", "=", "<", ">", "<=", ">=", 
        "and", "AND", "or", "OR", "not", "NOT"
    ));

    

    public static String[] tokenizar(String expresion) {
        String modificada = expresion.replace("(", " ( ").replace(")", " ) ");
        return modificada.trim().split("\\s+");
    }

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

    public static boolean esPalabraReservada(String palabra) {
        return PALABRAS_RESERVADAS.contains(palabra.toLowerCase());
    }
}