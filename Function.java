import java.util.Map;

/**
 * Clase que representa una función definida por el usuario en LISP.
 * Almacena los parámetros, el cuerpo de la función y el intérprete asociado.
 */
public class Function {
    //------------------------------------------------------------------------------------------------------------------------  
    // Atributos de la clase Function
    private Object[] parameters;
    private Object body;
    private Interpreter interpreter;

    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Constructor de la clase Function.
     */
    public Function(Object[] parameters, Object body, Interpreter interpreter) {
        this.parameters = parameters;
        this.body = body;
        this.interpreter = interpreter;
    }
    
    //------------------------------------------------------------------------------------------------------------------------  
    /**
     * Ejecuta la función con los argumentos proporcionados.
     */
    public Object call(Object[] args, Map<String, Object> environment, Interpreter callingInterpreter) {
        // Crear un intérprete temporal que inicialmente copia el entorno global
        Interpreter tempInterpreter = new Interpreter();
        tempInterpreter.getEnvironment().putAll(environment);
        
        // Evaluar cada argumento usando el intérprete temporal y asignarlo a su parámetro
        for (int i = 0; i < parameters.length && i < args.length; i++) {
            Object evaluatedArg = Evaluator.evaluate(args[i], tempInterpreter);
            tempInterpreter.getEnvironment().put(parameters[i].toString(), evaluatedArg);
        }
        
        // Evaluar el cuerpo de la función con el intérprete temporal que ya tiene las variables locales
        return Evaluator.evaluate(body, tempInterpreter);
    }
    //------------------------------------------------------------------------------------------------------------------------  
}