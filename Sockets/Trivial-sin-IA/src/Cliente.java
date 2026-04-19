import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {

        // IP configurable: Ponemos la ip de nuestro ordenador, por seguridad voy a poner localhost, ya que esta tarea va hacer publicada en github
        String ip = "localhost";
        if (args.length > 0) {
            ip = args[0];
        }

        try (
                Socket socket = new Socket(ip, 5000);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        ) {

            System.out.println("Conectado al servidor.");

            // Le pedimos pedimos el nick al cliente y lo enviamos al servidor
            System.out.print("Introduce tu nick: ");
            String nick = sc.readLine();
            out.println(nick);

            // Hilo de ejecución secundario para la escucha continua de peticiones en la red.
            new ReceptorMensajes(in).start();

            System.out.println("Esperando a que el administrador inicie la partida...");

            // El hilo principal entra en estado de bloqueo a la espera de entrada por consola.
            while (true) {
                String respuesta = sc.readLine(); // Llamada bloqueante: lectura del búfer de entrada.
                if (respuesta != null) {
                    out.println("RESPUESTA|" + respuesta);
                }
            }

        } catch (IOException e) {
            System.err.println("Error en conexion: " + e.getMessage());
        }
    }
}