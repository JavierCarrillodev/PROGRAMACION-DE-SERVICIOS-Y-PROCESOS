import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    public static void main(String[] args) throws IOException {
        System.out.println("Esperando...");
        try(
                ServerSocket servidor = new ServerSocket(1234);
                Socket cliente = servidor.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter((cliente.getOutputStream()),true);

        ){

            System.out.println("Cliente conectado con exito");

            String linea;
            System.out.println("Escribe exit para salir");
            while ((linea = in.readLine()) !=null){
                if (linea.equalsIgnoreCase("exit")){
                    out.println("adios");
                    break;
                }
                System.out.println("Cliente: " + linea);

                out.println("Mensaje '" + linea + "' recibido correctamente.");

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}
