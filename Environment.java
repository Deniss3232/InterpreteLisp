import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, LispFunction> functions = new HashMap<>();

    public void define(String name, Object value) {
        variables.put(name, value);
    }

    public void define(String name, LispFunction func) {
        functions.put(name, func);
    }

    public Object lookup(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable no definida: " + name);
    }

    public LispFunction lookupFunction(String name) {
        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        throw new RuntimeException("Función no definida: " + name);
    }
}
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, LispFunction> functions = new HashMap<>();

    public void define(String name, Object value) {
        variables.put(name, value);
    }

    public void define(String name, LispFunction func) {
        functions.put(name, func);
    }

    public Object lookup(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable no definida: " + name);
    }

    public LispFunction lookupFunction(String name) {
        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        throw new RuntimeException("Función no definida: " + name);
    }
}