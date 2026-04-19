import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(1234)
                ){
            System.out.println("Servidor escuchando en el puerto 1234");

            while (true){
                Socket cliente = server.accept();
                System.out.println("¡Nuevo cliente conectado desde la direccion " + cliente.getInetAddress() + "!");

                // Iniciamos el hilo sin bloquear el servidor
                new ClienteHandler(cliente).start();
            }

        }catch (Exception e) { e.printStackTrace(); }
    }
}