import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Clase que contiene pruebas unitarias para verificar el correcto funcionamiento
 * de las diferentes componentes del intérprete LISP.
 */
public class ProjectTests {
    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la funcionalidad de Parser.parse.
     */
    @Test
    public void testParserParse() {
        String expr = "(+ 1 2)";
        String[] tokens = Lexer.tokenizar(expr);
        Object parsed = Parser.parse(tokens);
        assertTrue(parsed instanceof List<?>);
        List<?> list = (List<?>) parsed;
        assertEquals(3, list.size());
        assertEquals("+", list.get(0));
        assertEquals("1", list.get(1));
        assertEquals("2", list.get(2));
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la operación de suma en el intérprete.
     */
    @Test
    public void testAddition() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(+ 1 2)");
        assertEquals(3, result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la operación de resta en el intérprete.
     */
    @Test
    public void testSubtraction() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(- 5 2)");
        assertEquals(3, result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la operación de multiplicación en el intérprete.
     */
    @Test
    public void testMultiplication() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(* 2 3)");
        assertEquals(6, result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la operación de división en el intérprete.
     */
    @Test
    public void testDivision() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(/ 8 2)");
        assertEquals(4, result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba el operador de igualdad.
     */
    @Test
    public void testEquality() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(= 3 3)");
        assertEquals(true, result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la asignación de variables con setq.
     */
    @Test
    public void testSetq() {
        Interpreter interpreter = new Interpreter();
        Object setqResult = interpreter.evaluate("(setq x 10)");
        assertEquals(10.0, setqResult);
        // Luego, al evaluar "x" debe devolver 10
        Object xValue = interpreter.evaluate("x");
        assertEquals(10.0, xValue);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba el operador quote.
     */
    @Test
    public void testQuote() {
        Interpreter interpreter = new Interpreter();
        Object result = interpreter.evaluate("(quote (1 2 3))");
        assertTrue(result instanceof List<?>);
        List<?> list = (List<?>) result;
        assertEquals(3, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la definición y llamada de funciones con defun.
     */
    @Test
    public void testDefunAndCall() {
        Interpreter interpreter = new Interpreter();
        // Definir la función square: (defun square (x) (* x x))
        Object defunResult = interpreter.evaluate("(defun square (x) (* x x))");
        assertEquals("Function square defined.", defunResult);
        // Llamar a la función: (square 5) debería devolver 25
        Object callResult = interpreter.evaluate("(square 5)");
        assertEquals(25, callResult);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba el operador cond.
     */
    @Test
    public void testCond() {
        Interpreter interpreter = new Interpreter();
        // La expresión cond evaluará la primera condición falsa y luego la verdadera (t)
        Object result = interpreter.evaluate("(cond ((< 1 0) \"No\") (t \'Yes\'))");
        assertEquals("'Yes'", result);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la detección de paréntesis no balanceados.
     */
    @Test
    public void testUnbalancedParentheses() {
        Interpreter interpreter = new Interpreter();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            interpreter.evaluate("(+ 1 2");
        });
        String expectedMessage = "No están balanceados los paréntesis";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
    //------------------------------------------------------------------------------------------------------------------------  
/**
     * Prueba la función tokenizar de la clase Lexer.
     */
    @Test
    public void testTokenizar() {
        String expr = "(+ 1 2)";
        String[] tokens = Lexer.tokenizar(expr);
        assertArrayEquals(new String[]{"(", "+", "1", "2", ")"}, tokens);
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la función buscarPalabrasReservadas de la clase Lexer.
     */
    @Test
    public void testBuscarPalabrasReservadas() {
        String expr = "(if (> 5 3) (print 5))";
        List<String> reserved = Lexer.buscarPalabrasReservadas(expr);
        // Se espera encontrar palabras como "if", ">" y "print" (todo en minúscula)
        assertTrue(reserved.contains("if"));
        assertTrue(reserved.contains(">"));
        assertTrue(reserved.contains("print"));
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la función esPalabraReservada de la clase Lexer.
     */
    @Test
    public void testEsPalabraReservada() {
        assertTrue(Lexer.esPalabraReservada("if"));
        assertFalse(Lexer.esPalabraReservada("variable"));
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la función cantParentesis de la clase Utils con paréntesis balanceados.
     */
    @Test
    public void testCantParentesisBalanced() {
        assertTrue(Utils.cantParentesis("(+ 1 2)"));
    }

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Prueba la función cantParentesis de la clase Utils con paréntesis no balanceados.
     */
    @Test
    public void testCantParentesisUnbalanced() {
        assertFalse(Utils.cantParentesis("(()"));
    }
}
    //------------------------------------------------------------------------------------------------------------------------