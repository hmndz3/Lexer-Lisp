import java.util.Map;

public class Function {
    private Object[] parameters;
    private Object body;
    private Interpreter interpreter;

    public Function(Object[] parameters, Object body, Interpreter interpreter) {
        this.parameters = parameters;
        this.body = body;
        this.interpreter = interpreter;
    }
    
    // Se agrega el parámetro callingInterpreter para usar el entorno de llamada
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
    
}