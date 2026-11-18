# Registro de empleados

Tarea de programacion orientada a objetos en Java. Registra empleados por hora,
temporales y de planta, calcula el sueldo de cada uno y permite borrarlos de la lista.

## Como se ejecuta

Abrir Main.java en VS Code y darle Run. O por consola:

    javac -d out src/*.java
    java -cp out Main

## Clases

- Empleado: clase madre abstracta con los datos comunes.
- EmpPorHor, EmpTem, EmpPer: cada una con su calculo de sueldo.
- Ventana: la interfaz y los eventos.