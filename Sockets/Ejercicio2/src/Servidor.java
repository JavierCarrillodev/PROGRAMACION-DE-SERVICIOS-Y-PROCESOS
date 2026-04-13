import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
    public static void main(String[] args) {
        int puerto = 7000;
        System.out.println("Servidor arrancado y esperando en el puerto " + puerto + "...");

        try (
                ServerSocket serverSocket = new ServerSocket(puerto);
                // Espera a un cliente
                Socket cliente = serverSocket.accept();
                // Lee datos del cliente
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(cliente.getInputStream()));
                // Autoflush en true para que se limpie el buffer tras enviar mensaje
                PrintWriter out = new PrintWriter(
                        cliente.getOutputStream(), true)
        ) {
            System.out.println("Cliente conectado");
            List<Integer> numeros = new ArrayList<>();

            String operacion;

            while ((operacion = in.readLine()) != null && !operacion.equals("6")) {
                switch (operacion) {
                    case "1":
                        // Pedimos el número
                        out.println("Agregar");
                        String numeroAgregar = in.readLine();
                        numeros.add(Integer.parseInt(numeroAgregar));
                        System.out.println("Número " + numeroAgregar + " agregado a la lista con exito.");
                        break;

                    case "2":
                        if (numeros.isEmpty()) {
                            out.println("La lista está vacía.");
                        } else {
                            out.println("Números en la lista: " + numeros.toString());
                        }
                        break;

                    case "3":
                        if (numeros.isEmpty()) {
                            out.println("No se puede calcular la media porque la lista está vacía.");
                        } else {
                            int total = 0;
                            for (int n : numeros) {
                                total += n;
                            }
                            out.println("La media de los números es: " + (total / numeros.size()));
                        }
                        break;

                    case "4":
                        if (numeros.isEmpty()) {
                            out.println("No hay máximo, la lista está vacía.");
                        } else {
                            // Asume el primero como mayor
                            int mayor = numeros.get(0);
                            for (int n : numeros) {
                                if (n > mayor) {
                                    mayor = n;
                                }
                            }
                            out.println("El número mayor de la lista es: " + mayor);
                        }
                        break;

                    case "5":
                        numeros.clear();
                        out.println("La lista se ha vaciado correctamente.");
                        break;
                    default:
                        out.println("Operación no válida. Inténtalo de nuevo.");
                }
            }
            out.println("Cerrando sesión...");

        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        }
    }
}