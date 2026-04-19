import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 1234);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner teclado = new Scanner(System.in);
                )
        {
            // Mensaje para cuando el cliente se conecte al servidor
            System.out.println("Conectado al servidor");
            String opcion;

            while (true) {
                // Mostramos el menú
                System.out.println("------Menú-------:" +
                        "\n 1. Incrementar contador" +
                        "\n 2. Obtener contador" +
                        "\n 3. Resetear contador" +
                        "\nEscribe 'Exit' para salir");

                // Leemos la opción que elija el cliente
                opcion = teclado.nextLine();

                // Se compruena si salimos del bucle
                if (opcion == null || opcion.equalsIgnoreCase("Exit")) {
                    if (opcion != null) {
                        out.println(opcion);

                        System.out.println("Servidor: " + in.readLine());
                    }
                    // Cerramos todo con el bucle
                    break;
                }

                switch (opcion){
                    case "1":
                        out.println(opcion);

                        System.out.println("Servidor: " + in.readLine());
                        break;
                    case "2":
                        out.println(opcion);

                        System.out.println("Servidor: Contador: " + in.readLine());
                        break;
                    case "3":
                        out.println(opcion);

                        System.out.println("Servidor: " + in.readLine());
                        break;
                    default:
                        out.println(opcion);

                        System.out.println("Servidor: " + in.readLine());
                        break;
                }

            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}