import java.util.HashMap;
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
        Map<String, Object> localEnv = new HashMap<>(environment);
        for (int i = 0; i < parameters.length; i++) {
            localEnv.put(parameters[i].toString(), args[i]);
        }
        return Evaluator.evaluate(body, interpreter);
    }
}