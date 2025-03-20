import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();

        while (true) {
            System.out.println("Ingrese una expresión LISP (o 'salir' para terminar):");
            String linea = lector.nextLine();

            if (linea.trim().equalsIgnoreCase("salir")) {
                break;
            }

            StringBuilder constructorExpresion = new StringBuilder();
            constructorExpresion.append(linea).append(" ");

            String expresionCompleta = constructorExpresion.toString().trim();
            if (expresionCompleta.isEmpty()) {
                System.out.println("No ingresó ninguna expresión.");
                continue;
            }

            if (!Utils.cantParentesis(expresionCompleta)) {
                System.out.println("No están balanceados los paréntesis, revisar expresión ingresada");
                continue;
            }

            System.out.println("\nExpresión que ingresó: " + expresionCompleta);
            List<String> palabrasReservadas = Lexer.buscarPalabrasReservadas(expresionCompleta);
            System.out.println("\nPalabras reservadas encontradas:");
            if (palabrasReservadas.isEmpty()) {
                System.out.println("No se encontraron palabras reservadas en la expresión.");
            } else {
                for (String palabra : palabrasReservadas) {
                    System.out.println("'" + palabra + "' es una palabra reservada de LISP");
                }
            }

            Object resultado = interpreter.evaluate(expresionCompleta);
            System.out.println("\nResultado de la evaluación:");
            System.out.println(resultado);
        }

        lector.close();
    }
}