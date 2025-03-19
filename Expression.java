import java.util.ArrayList;
import java.util.List;

public class Expression {
    private final List<Object> elements;

    public Expression() {
        this.elements = new ArrayList<>();
    }

    public void add(Object obj) {
        elements.add(obj);
    }

    public Object get(int index) {
        return elements.get(index);
    }

    public int size() {
        return elements.size();
    }

    public static Expression parse(String input) {
        input = input.trim();
        if (input.isEmpty()) {
            throw new IllegalArgumentException("La expresión está vacía.");
        }

        if (input.startsWith("(") && input.endsWith(")")) {
            input = input.substring(1, input.length() - 1).trim();
            Expression exp = new Expression();
            int depth = 0;
            StringBuilder token = new StringBuilder();
            List<String> tokens = new ArrayList<>();

            for (char c : input.toCharArray()) {
                if (c == '(') depth++;
                if (c == ')') depth--;

                if (c == ' ' && depth == 0) {
                    if (token.length() > 0) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                } else {
                    token.append(c);
                }
            }

            if (token.length() > 0) {
                tokens.add(token.toString());
            }

            for (String t : tokens) {
                exp.add(parseToken(t));
            }

            return exp;
        } else {
            return new Expression() {{
                add(parseToken(input));
            }};
        }
    }

    private static Object parseToken(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            return token; // Es una variable o un operador
        }
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
