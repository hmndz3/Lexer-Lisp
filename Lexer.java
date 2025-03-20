import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Lexer {
    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>(Arrays.asList(
        "defun", "setq", "if", "cond", "let", "car", "cdr", "cons", "quote",
        "lambda", "print", "eval", "apply", "list", "append", "null", "atom",
        "+", "-", "*", "/", "=", "<", ">", "<=", ">=", "and", "or", "not"
    ));

    public static boolean esPalabraReservada(String palabra) {
        return PALABRAS_RESERVADAS.contains(palabra.toLowerCase());
    }

    public static void buscarPalabrasReservadas(String expresion) {
        System.out.println("\nPalabras reservadas encontradas:");
        boolean encontroPalabrasReservadas = false;
        String[] palabras = expresion.replaceAll("[()]", " ").split("\\s+");

        for (String palabra : palabras) {
            if (!palabra.isEmpty() && esPalabraReservada(palabra)) {
                System.out.println("'" + palabra + "' es una palabra reservada de LISP");
                encontroPalabrasReservadas = true;
            }
        }

        if (!encontroPalabrasReservadas) {
            System.out.println("No se encontraron palabras reservadas en la expresión.");
        }
    }

    public static String[] tokenizar(String expresion) {
        String modificada = expresion.replace("(", " ( ").replace(")", " ) ");
        String[] tokens = modificada.trim().split("\\s+");

        System.out.println("\nTokens obtenidos:");
        System.out.print("[");
        for (int i = 0; i < tokens.length; i++) {
            System.out.print(tokens[i]);
            if (i < tokens.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        return tokens;
    }
}