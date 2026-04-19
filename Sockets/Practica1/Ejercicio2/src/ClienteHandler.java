import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteHandler extends Thread{
    private Socket cliente;
    private static int contador = 0;

    // Recibimos el socket de la conexión
    public ClienteHandler(Socket socket) {
        this.cliente = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);)
        {
            // Espero y leo la orden del cliente
            String operacion;
            while ((operacion = in.readLine()) != null && !operacion.equalsIgnoreCase("Exit")){

                String cadena = null;
                switch (operacion){
                    case "1":
                        incrementar();
                        out.println("Contador incrementado");
                        break;
                    case "2":
                        out.println(consultar());
                        break;
                    case "3":
                        resetear();
                        out.println("Contador reseteado");
                        break;
                    default:
                        out.println("Operación no válida");
                }
            }
            out.println("Chao Pescao");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized void incrementar(){
        contador ++;
    }

    public static synchronized int consultar(){
        return contador;
    }

    public static synchronized void resetear(){
        contador = 0;
    }
}