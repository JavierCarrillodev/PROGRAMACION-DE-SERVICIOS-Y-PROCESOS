package Ejercicio1;

import java.io.IOException;

/*
Crea un programa en Java que crea un hilo y abra firefox o un programa que ya tenéis
abierto y se queda esperando hasta que se cierre. Una vez se cierra escribe un mensaje en
pantalla de que el programa ha sido cerrado y acaba la ejecución.
Nota: Si descubrís algún problema investigad la causa y también cual es una posible
*/
public class Ejer1 extends  Thread {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("gedit");
            Process proceso = pb.start();
            System.out.println("Proceso bloc de nota abierto");

            int exitCode = proceso.waitFor();

            System.out.println("Proceso cerrado con codigo " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
