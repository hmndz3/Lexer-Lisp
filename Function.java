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

    public Object call(Object[] args, Map<String, Object> environment) {
        // Crear una nueva instancia de Interpreter con el entorno actual
        Interpreter localInterpreter = new Interpreter();
        Map<String, Object> localEnv = localInterpreter.getEnvironment();
        
        // Copiar el entorno global
        localEnv.putAll(environment);
        
        // Agregar los parámetros al entorno local
        for (int i = 0; i < parameters.length; i++) {
            // Evaluar los argumentos
            Object evaluatedArg = Evaluator.evaluate(args[i], interpreter);
            localEnv.put(parameters[i].toString(), evaluatedArg);
        }
        
        // Evaluar el cuerpo usando el intérprete local
        return Evaluator.evaluate(body, localInterpreter);
    }
}