import java.util.List;
import java.util.stream.Collectors;

public class Interpreter {
    private final Environment globalEnv;

    public Interpreter() {
        globalEnv = new Environment();
        setupGlobalEnvironment();
    }

    private void setupGlobalEnvironment() {
        globalEnv.define("+", (LispFunction) args -> args.stream()
                .mapToInt(a -> (int) a)
                .sum());

        globalEnv.define("-", (LispFunction) args -> {
            if (args.isEmpty()) {
                throw new IllegalArgumentException("La operación de resta necesita al menos un argumento.");
            }
            int result = (int) args.get(0);
            for (int i = 1; i < args.size(); i++) {
                result -= (int) args.get(i);
            }
            return result;
        });

        globalEnv.define("*", (LispFunction) args -> args.stream()
                .mapToInt(a -> (int) a)
                .reduce(1, (a, b) -> a * b));

        globalEnv.define("/", (LispFunction) args -> {
            if (args.size() < 2) {
                throw new IllegalArgumentException("La operación de división necesita al menos dos argumentos.");
            }
            int result = (int) args.get(0);
            for (int i = 1; i < args.size(); i++) {
                int divisor = (int) args.get(i);
                if (divisor == 0) {
                    throw new ArithmeticException("Error: División por cero.");
                }
                result /= divisor;
            }
            return result;
        });

        globalEnv.define("quote", (LispFunction) args -> args.get(0));

        globalEnv.define("print", (LispFunction) args -> {
            args.forEach(System.out::println);
            return null;
        });

        globalEnv.define("setq", (LispFunction) args -> {
            if (args.size() != 2) {
                throw new IllegalArgumentException("Error en setq: Debe tener 2 argumentos (variable y valor).");
            }
            String varName = (String) args.get(0);
            Object value = evaluate(args.get(1), globalEnv);
            globalEnv.define(varName, value);
            return value;
        });

        globalEnv.define("defun", (LispFunction) args -> {
            if (args.size() != 3) {
                throw new IllegalArgumentException("Error en defun: Debe tener 3 argumentos (nombre, parámetros y cuerpo).");
            }
            String functionName = (String) args.get(0);
            Expression params = (Expression) args.get(1);
            Expression body = (Expression) args.get(2);
            Function function = new Function(params, body, globalEnv);
            globalEnv.define(functionName, function);
            return functionName;
        });

        globalEnv.define("cond", (LispFunction) args -> {
            for (int i = 0; i < args.size(); i += 2) {
                if (i + 1 >= args.size()) {
                    throw new IllegalArgumentException("Error en cond: Falta la acción para la condición.");
                }
                boolean condition = (boolean) evaluate(args.get(i), globalEnv);
                if (condition) {
                    return evaluate(args.get(i + 1), globalEnv);
                }
            }
            return null;
        });
    }

    public Object evaluate(Object exp) {
        return evaluate(exp, globalEnv);
    }

    public Object evaluate(Object exp, Environment env) {
        if (exp instanceof String) {
            return env.lookup((String) exp);
        } else if (exp instanceof Expression) {
            Expression list = (Expression) exp;
            if (list.size() == 0) {
                throw new IllegalArgumentException("Error: Se intentó evaluar una lista vacía.");
            }

            Object first = list.get(0);
            Object functionObject = evaluate(first, env);

            if (!(functionObject instanceof LispFunction)) {
                throw new IllegalArgumentException("Error: " + first + " no es una función válida.");
            }

            LispFunction function = (LispFunction) functionObject;
            List<Object> args = list.stream()
                    .skip(1)
                    .map(arg -> evaluate(arg, env))
                    .collect(Collectors.toList());

            return function.apply(args);
        } else {
            return exp;
        }
    }
}
