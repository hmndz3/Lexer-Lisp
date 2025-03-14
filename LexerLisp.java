import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LexerLisp {
    
    // Lista de palabras reservadas en LISP.
    private static final Set<String> PALABRAS_RESERVADAS = new HashSet<>(Arrays.asList(
        "defun", "setq", "if", "cond", "let", "car", "cdr", "cons", "quote",
        "lambda", "print", "eval", "apply", "list", "append", "null", "atom",
        "+", "-", "*", "/", "=", "<", ">", "<=", ">=", "and", "or", "not"
    ));
    
    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        StringBuilder constructorExpresion = new StringBuilder();
        System.out.println("Ingrese una expresión LISP.");
        System.out.println("Para terminar, deje una línea vacía y presione Enter:");
        String linea;
        
        // Bucle para leer todas las líneas hasta encontrar una vacía
        while (true) {
            linea = lector.nextLine();            
            if (linea.trim().isEmpty()) {
                break;
            }            
            constructorExpresion.append(linea).append(" ");
        }
        // Eliminar espacios o cosas extra.
        String expresionCompleta = constructorExpresion.toString().trim();
        // Programacion defensiva en caso de que el usuario no ingrese nada.
        if (expresionCompleta.isEmpty()) {
            System.out.println("No ingresó ninguna expresión.");
            lector.close();
            return;
        }
    
        if (!cantParentesis(expresionCompleta)) {
            System.out.println("No estan balanceados los parentesis, revisar expresion ingresada");
            lector.close();
            return;
        }
    
        // Expresion que el usuario mando.
        System.out.println("\nExpresión que ingresó: " + expresionCompleta);
        
        // Buscamos palabras reservadas en la expresión
        buscarPalabrasReservadas(expresionCompleta);
        
        // Llamada al método de tokenización
        String[] tokens = tokenizar(expresionCompleta);
        
        // Parsear los tokens y mostrar la estructura de listas
        String resultadoParseo = parseLisp(tokens);
        System.out.println("\nEstructura parseada:");
        System.out.println(resultadoParseo);
        
        lector.close();
    }
    
    /**
     * Metodo que conprueba si una palabra es reservada en LISP.
     */
    public static boolean esPalabraReservada(String palabra) {
        // Convertimos a minúscula y comparamos con nuestra lista
        return PALABRAS_RESERVADAS.contains(palabra.toLowerCase());
    }
    
    /**
     * Metodo para buscar palabras reservadas en la expresión
     */
    public static void buscarPalabrasReservadas(String expresion) {
        System.out.println("\nPalabras reservadas encontradas:");
        boolean encontroPalabrasReservadas = false;
        // Quitamos los paréntesis y dividimos por espacios
        String[] palabras = expresion.replaceAll("[()]", " ").split("\\s+");
        
        // Revisamos cada palabra
        for (String palabra : palabras) {
            if (!palabra.isEmpty() && esPalabraReservada(palabra)) {
                System.out.println("'" + palabra + "' es una palabra reservada de LISP");
                encontroPalabrasReservadas = true;
            }
        }
        // Si no se encontro ninguna paralabra reservadaa.
        if (!encontroPalabrasReservadas) {
            System.out.println("No se encontraron palabras reservadas en la expresión.");
        }
    }

    // verificador de la entrada en cuanto a cantidad de parentesis, debe existir un balance de 0 para que sea correcto 
    public static boolean cantParentesis(String expresionIngresada) {
        int balanceParentesis = 0;

        for (int i = 0; i < expresionIngresada.length(); i++) {
            char simbolos = expresionIngresada.charAt(i);

            // por cada parentesis suma o resta al contador para verificar su balance
            switch (simbolos) {
                case '(':
                    balanceParentesis++;
                    break;
                case ')':
                    balanceParentesis--;
                    break;
            }

            if (balanceParentesis < 0) {
                return false;
            }
        }
        return balanceParentesis == 0;
    }

    public static String[] tokenizar(String expresion) {
        // Agregamos espacios alrededor de cada paréntesis para asegurarnos que queden como tokens independientes.
        String modificada = expresion.replace("(", " ( ").replace(")", " ) ");
        // Separamos la cadena por espacios en blanco.
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
        
        return tokens; // Ahora retornamos los tokens para usarlos en el parser
    }

//---------------------------------------------------------------------Nuevos metodos para el parser.
    public static String parseLisp(String[] tokens) {
        int[] pos = {0}; //Usamos un array para que el índice sea modificable por referencia
        return parseExpression(tokens, pos);
    }

    private static String parseExpression(String[] tokens, int[] pos) {
        //Verificar que estamos en un paréntesis de apertura
        if (pos[0] >= tokens.length || !tokens[pos[0]].equals("(")) {
            return "Error: Se esperaba '(' en la posición " + pos[0];
        }
        
        //Saltar el paréntesis de apertura
        pos[0]++;
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("[");
        
        // Obtener el operador o nombre de función
        if (pos[0] >= tokens.length) {
            return "Error: Fin inesperado de la entrada después de '('";
        }
        resultado.append(tokens[pos[0]]);
        pos[0]++;
        
        // Procesar argumentos
        while (pos[0] < tokens.length && !tokens[pos[0]].equals(")")) {
            resultado.append(" "); // Espacio entre elementos
            if (tokens[pos[0]].equals("(")) {
                resultado.append(parseExpression(tokens, pos));
            } else {
                resultado.append(tokens[pos[0]]);
                pos[0]++;
            }
        }
        // Verificar y saltar el paréntesis de cierre
        if (pos[0] >= tokens.length || !tokens[pos[0]].equals(")")) {
            return "Error: Se esperaba ')' en la posición " + pos[0];
        }
        pos[0]++; 
        resultado.append("]");
        return resultado.toString();
    }
}