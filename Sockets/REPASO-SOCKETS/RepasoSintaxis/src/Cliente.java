import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){
     try(
             Socket socket = new Socket("localhost",1234);
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sc = new Scanner(System.in);
             ){
         System.out.println("Conectado al servidor");

         String mensaje;
         while (true){
             System.out.println("Tu:");
             mensaje = sc.nextLine();
             out.println(mensaje);
             if (mensaje.equalsIgnoreCase("exit")){
                 System.out.println("Servidor:" + in.readLine());
                 System.out.println("Cerrando cliente...");
                 break;
             }
             String repuesta = in.readLine();
             System.out.println("Servidor: " + repuesta);
         }


     }catch (UnknownHostException e){
         System.err.println("Error:Servidor desconocido");
     }catch (IOException e){
         System.err.println("Error de conexion. Servidor no encendido");
     }
    }
}
