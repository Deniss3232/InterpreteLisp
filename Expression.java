import java.util.ArrayList;
import java.util.List;

public class Expression extends ArrayList<Object> {
    public static Expression parse(String input) {
        input = input.trim();
        if (input.startsWith("(") && input.endsWith(")")) {
            input = input.substring(1, input.length() - 1).trim();
            Expression exp = new Expression();
            int depth = 0;
            StringBuilder token = new StringBuilder();
            for (char c : input.toCharArray()) {
                if (c == '(') depth++;
                if (c == ')') depth--;
                if (c == ' ' && depth == 0) {
                    exp.add(parse(token.toString()));
                    token.setLength(0);
                } else {
                    token.append(c);
                }
            }
            if (token.length() > 0) {
                exp.add(parse(token.toString()));
            }
            return exp;
        } else {
            try {
                return new Expression() {{
                    add(Integer.parseInt(input));
                }};
            } catch (NumberFormatException e) {
                return new Expression() {{
                    add(input);
                }};
            }
        }
    }
}
