import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Servidor levantado y esperando");
        try (
                ServerSocket serverSocket = new ServerSocket(7000);
                // Esperamos a que se conecte el cliente
                Socket cliente = serverSocket.accept();
                // Leemos los mensajes del cliente
                BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream())
                );

                PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                ){

            System.out.println("Cliente conectado");

            boolean login = false;
            int intentos = 0;

            // VALIDACIÓN DE CREDENCIALES
            while (!login){
                if (intentos == 3){
                    System.out.println("Login incorrecto");
                    out.println("LOGIN_BAD");
                    break;
                }

                // Lectura de usuario
                System.out.println("Usuario: ");
                out.println("Usuario");

                // Llegada del usuario
                String usuarioRecibido = in.readLine();

                // Verificación contra datos en memoria
                System.out.println("Contraseña: ");
                out.println("password");
                String passwordRecibida = in.readLine();

                if (usuarioRecibido.equals("admin") && passwordRecibida.equals("1234") && intentos <= 3){
                    System.out.println("Login correcto");
                    out.println("LOGIN_OK");

                    login = true;
                }else {
                    System.out.println("Credenciales incorrectas, te quedan " + (3 - intentos) + " intentos.");
                    out.println("CREDENTIALS_BAD");
                    intentos ++;
                }
            }

            // Si es correcto, entramos al bucle
            if (login) {
                System.out.println("Menú:" +
                        "\n1.Sumar" +
                        "\n2.Contador" +
                        "\n3.Invierte" +
                        "\n4.EsPrimo" +
                        "\n5.Salir");
                String opcion;
                while ((opcion = in.readLine()) != null && !opcion.equals("5")) {
                    // Esperamos a la opción del cliente
                    System.out.println("Opción recibida del cliente: " + opcion);
                    switch (opcion){
                        case "1":
                            System.out.println("Introduce el primer número:");
                            out.println("n1_sumar");
                            // Recibimos el primer número
                            String numero1Recibido = in.readLine();

                            System.out.println ("Introduce el segundo número:");
                            out.println("n2_sumar");
                            // Recibimos el segundo número
                            String numero2Recibido = in.readLine();

                            double suma = (Double.parseDouble(numero1Recibido) + Double.parseDouble(numero2Recibido));

                            System.out.println("Resultado de la suma: " + suma);
                            out.println("Resultado de la suma: " + suma);
                            break;

                        case "2":
                            System.out.println("Introduce la palabra para contar sus vocales:");
                            // Le enviamos al cliente que debe introducir la palabra que el quiera
                            out.println("palabra_contador");
                            // Recibimos la palabra y contamos vocales
                            String palabraContador = in.readLine();

                            int contadorVocales = 0;
                            // Pasamos las vocales a minúsculas
                            String palabraMin = palabraContador.toLowerCase();

                            for (int i = 0; i < palabraMin.length(); i++) {
                                char c = palabraMin.charAt(i);
                                if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                                    contadorVocales++;
                                }
                            }

                            System.out.println("Número de vocales en la palabra " + palabraContador + ": " + contadorVocales);
                            out.println(contadorVocales);
                            break;

                        case "3":
                            System.out.println("Introduce texto para devolverlo invertido:");
                            out.println("texto_invertir");
                            String textoInvertir = in.readLine();
                            
                            String textoInvertido = "";
                            for (int i = textoInvertir.length() - 1; i >=0 ; i--) {
                                // Se suma cada letra al reves
                                textoInvertido += textoInvertir.charAt(i);
                            }

                            System.out.println("El texto invertido es: " + textoInvertido);
                            out.println(textoInvertido);
                            break;

                        case "4":
                            System.out.println("Introduce número para comprobar si es primo");
                            out.println("numero_primo");
                            String numeroPrimo = in.readLine();

                            int n = Integer.parseInt(numeroPrimo);

                            if (esPrimo(n)) {
                                System.out.println("El número " + n + " es primo");
                                out.println("El número " + n + " es primo");
                            } else {
                                System.out.println("El número " + n + " no es primo.");
                                out.println("El número " + n + " no es primo");
                            }

                            break;
                        case "5":
                            break;
                        default:
                            System.out.println("opción no válida");
                    }
                }
            }

            System.out.println("Cerrando sesión con el cliente...");


        } catch (IOException e) {
            System.err.println("Error de conexión");
            throw new RuntimeException(e);
        }
    }

    public static boolean esPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}