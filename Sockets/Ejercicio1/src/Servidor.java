import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor arrancado y esperando...");

        try (
                ServerSocket server = new ServerSocket(1234); // Creamos el servidor en el puerto 1234
        ){
            while (true){
                Socket cliente = server.accept(); // Pausa esperando a que se conecte un cliente
                new ClientHandler(cliente).start();
            }

        } catch (IOException e){
            System.err.println("Error de conexión");
        }
    }

}