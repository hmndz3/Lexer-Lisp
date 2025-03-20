

public class Parser {
    public static String parseLisp(String[] tokens) {
        int[] pos = {0};
        return parseExpression(tokens, pos);
    }

    private static String parseExpression(String[] tokens, int[] pos) {
        if (pos[0] >= tokens.length || !tokens[pos[0]].equals("(")) {
            return "Error: Se esperaba '(' en la posición " + pos[0];
        }

        pos[0]++;
        StringBuilder resultado = new StringBuilder();
        resultado.append("[");

        if (pos[0] >= tokens.length) {
            return "Error: Fin inesperado de la entrada después de '('";
        }
        resultado.append(tokens[pos[0]]);
        pos[0]++;

        while (pos[0] < tokens.length && !tokens[pos[0]].equals(")")) {
            resultado.append(" ");
            if (tokens[pos[0]].equals("(")) {
                resultado.append(parseExpression(tokens, pos));
            } else {
                resultado.append(tokens[pos[0]]);
                pos[0]++;
            }
        }

        if (pos[0] >= tokens.length || !tokens[pos[0]].equals(")")) {
            return "Error: Se esperaba ')' en la posición " + pos[0];
        }
        pos[0]++;
        resultado.append("]");
        return resultado.toString();
    }
}