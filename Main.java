import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido al intérprete LISP. Escribe una expresión o 'exit' para salir.");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Object result = interpreter.evaluate(input);
                System.out.println("Resultado: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
