import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        StringBuilder constructorExpresion = new StringBuilder();
        System.out.println("Ingrese una expresión LISP.");
        System.out.println("Para terminar, deje una línea vacía y presione Enter:");
        String linea;

        while (true) {
            linea = lector.nextLine();
            if (linea.trim().isEmpty()) {
                break;
            }
            constructorExpresion.append(linea).append(" ");
        }

        String expresionCompleta = constructorExpresion.toString().trim();
        if (expresionCompleta.isEmpty()) {
            System.out.println("No ingresó ninguna expresión.");
            lector.close();
            return;
        }

        if (!Utils.cantParentesis(expresionCompleta)) {
            System.out.println("No estan balanceados los parentesis, revisar expresion ingresada");
            lector.close();
            return;
        }

        System.out.println("\nExpresión que ingresó: " + expresionCompleta);
        Lexer.buscarPalabrasReservadas(expresionCompleta);
        String[] tokens = Lexer.tokenizar(expresionCompleta);
        String resultadoParseo = Parser.parseLisp(tokens);
        System.out.println("\nEstructura parseada:");
        System.out.println(resultadoParseo);

        lector.close();
    }
}