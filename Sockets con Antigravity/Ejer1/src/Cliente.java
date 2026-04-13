
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        // Al definir el socket y los buffers aquí, Java los cierra solo
        try (Socket socket = new Socket("localhost", 1234);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Conectado al servidor.");

            java.util.Scanner sc = new java.util.Scanner(System.in);

            System.out.print("Introduce operación (CIFRAR o DESCIFRAR): ");
            String operacion = sc.nextLine();
            out.println(operacion);

            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            if (respuesta != null && respuesta.equals("Pasame la clave")) {
                System.out.print("Introduce la clave numérica (ej. 3): ");
                out.println(sc.nextLine());

                respuesta = in.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);

                if (respuesta != null && respuesta.equals("Pasame la cadena")) {
                    System.out.print("Introduce el texto: ");
                    out.println(sc.nextLine());

                    String resultado = in.readLine();
                    System.out.println("Mensaje resultante: " + resultado);
                }
            } else {
                System.out.println("Operación fallida o desconocida.");
            }
        } catch (IOException e) {
            System.err.println("Error en conexión");
        } // Aquí se cierra el socket automáticamente
    }
}