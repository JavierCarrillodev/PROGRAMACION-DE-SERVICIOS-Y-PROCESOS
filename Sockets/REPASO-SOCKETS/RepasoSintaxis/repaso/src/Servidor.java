import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Esperand0 a cliente...");

        try(
                ServerSocket server = new ServerSocket(1234);
                Socket cliente = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                PrintWriter out = new PrintWriter(new PrintWriter(cliente.getOutputStream()),true)

        ) {
            System.out.println("Cliente sentado en la mesa");

            String linea;
            while ((linea = in.readLine()) !=null){

                System.out.println("Cliente: " + linea);
                System.out.println("Oido coina '" + linea + "' recibido");
                out.println("Oido coina '" + linea + "' recibido");

            }

        }catch (UnknownHostException e){
            System.err.println("Error: El servidor esta encendido?");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
