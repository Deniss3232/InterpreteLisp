# Intérprete LISP en Java

Este es un intérprete simple de LISP en Java que soporta operaciones básicas como `+`, `-`, `quote`, `print`, `setq` y `cond`, además de la definición de funciones (`defun`).

## Cómo usarlo
compila
javac -d out src/main/java/*.java

ejecuta el interprete
java -cp out Main

Escribe expresiones LISP en la terminal, por ejemplo:
> (+ 3 5)
Resultado: 8

> (setq x 10)
Resultado: 10

### Compilar y ejecutar

1. Clona el repositorio:
   ```sh
   git clone https://github.com/Elbiza21/InterpreteLisp.git
