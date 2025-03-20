import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Object parse(String[] tokens) {
        int[] pos = {0};
        return parseExpression(tokens, pos);
    }

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
}