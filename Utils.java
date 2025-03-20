public class Utils {
    public static boolean cantParentesis(String expresionIngresada) {
        int balanceParentesis = 0;
        for (int i = 0; i < expresionIngresada.length(); i++) {
            char simbolos = expresionIngresada.charAt(i);
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
}