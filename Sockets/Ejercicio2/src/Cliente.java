

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        System.out.println("Iniciando cliente...");

        try (
                Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))
        ) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Conectado al servidor con éxito.\n");
            String opcion = "";

            // Bucle del menú para mantener al cliente hasta que se introduzca el número 6
            while (!opcion.equals("6")) {
                System.out.println("--------------------------------");
                System.out.println("             MENÚ               ");
                System.out.println("--------------------------------");
                System.out.println("1. Añadir número");
                System.out.println("2. Mostrar lista");
                System.out.println("3. Calcular media");
                System.out.println("4. Buscar máximo");
                System.out.println("5. Borrar lista");
                System.out.println("6. Cerrar sesión");
                System.out.print("Elige una opción: ");

                opcion = sc.nextLine();
                out.println(opcion);

                if (opcion.equals("1")) {
                    // Esperamos confirmación
                    String indicacionServidor = in.readLine();

                    if (indicacionServidor != null && indicacionServidor.equals("Agregar")) {
                        System.out.print("Introduce el número a añadir: ");
                        String numero = teclado.readLine();

                        // Envíamos el número
                        out.println(numero);
                        System.out.println("Número enviado al servidor con exito.");
                    }

                } else if (opcion.equals("6")) {
                    System.out.println("\nServidor: " + in.readLine());

                } else {
                    String respuesta = in.readLine();
                    if (respuesta != null) {
                        System.out.println("\nServidor: " + respuesta);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error de conexión con el servidor. ¿Está encendido?");
        }
    }
}