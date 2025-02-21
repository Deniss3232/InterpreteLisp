import java.util.List;

public class Interpreter {
    private Environment globalEnv;

    public Interpreter() {
        globalEnv = new Environment();
        setupGlobalEnvironment();
    }

    private void setupGlobalEnvironment() {
        globalEnv.define("+", (args) -> args.stream().mapToInt(a -> (int) a).sum());

        globalEnv.define("-", (args) -> {
            int result = (int) args.get(0);
            for (int i = 1; i < args.size(); i++) {
                result -= (int) args.get(i);
            }
            return result;
        });

        globalEnv.define("quote", (args) -> args.get(0));

        globalEnv.define("print", (args) -> {
            args.forEach(System.out::println);
            return null;
        });

        globalEnv.define("setq", (args) -> {
            String varName = (String) args.get(0);
            Object value = evaluate(args.get(1), globalEnv);
            globalEnv.define(varName, value);
            return value;
        });

        globalEnv.define("cond", (args) -> {
            for (int i = 0; i < args.size(); i += 2) {
                if ((boolean) evaluate(args.get(i), globalEnv)) {
                    return evaluate(args.get(i + 1), globalEnv);
                }
            }
            return null;
        });
    }

    public Object evaluate(String input) {
        return evaluate(Expression.parse(input), globalEnv);
    }

    private Object evaluate(Object expr, Environment env) {
        if (expr instanceof Integer || expr instanceof String) {
            return expr;
        }

        if (expr instanceof List) {
            List<?> expList = (List<?>) expr;
            String operator = (String) expList.get(0);
            List<Object> args = expList.subList(1, expList.size());

            if (operator.equals("defun")) {
                String name = (String) args.get(0);
                Expression params = (Expression) args.get(1);
                Expression body = (Expression) args.get(2);
                env.define(name, new Function(params, body, env));
                return null;
            }

            LispFunction func = env.lookupFunction(operator);
            return func.apply(args);
        }

        throw new RuntimeException("Expresión no válida: " + expr);
    }
}
