import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Esperando conexión...");

        // Un solo try que gestiona todos los recursos en cascada
        try (ServerSocket server = new ServerSocket(1234);
                Socket cliente = server.accept(); // Se detiene aquí hasta que alguien entra
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)) {

            System.out.println("Cliente conectado!");

            String mensaje = in.readLine();

            String cadena = null;
            int clave = 3;
            if (mensaje != null) {
                mensaje = mensaje.toUpperCase();
                switch (mensaje) {
                    case "CIFRAR":
                    case "DESCIFRAR":
                        out.println("Pasame la clave");
                        try {
                            String claveLeida = in.readLine();
                            if (claveLeida != null)
                                clave = Integer.parseInt(claveLeida);
                        } catch (NumberFormatException e) {
                            out.println("Error procesando clave, asumiendo 3");
                        }
                        out.println("Pasame la cadena");
                        cadena = in.readLine();
                        if (cadena != null) {
                            out.println(cifrar_descifrar(cadena, mensaje, clave));
                        }
                        break;
                    default:
                        out.println("La operación solicitada no existe");
                }
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
        // Al llegar aquí, TODO (server, cliente, in, out) se ha cerrado
        // automáticamente.
    }

    static String cifrar_descifrar(String cadena, String operacion, int clave) {
        StringBuilder resultado = new StringBuilder();
        if (operacion.equals("DESCIFRAR")) {
            clave = -clave;
        }
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int offset = ((c - base) + clave) % 26;
                if (offset < 0) {
                    offset += 26;
                }
                c = (char) (base + offset);
            }
            resultado.append(c);
        }
        return resultado.toString();
    }
}