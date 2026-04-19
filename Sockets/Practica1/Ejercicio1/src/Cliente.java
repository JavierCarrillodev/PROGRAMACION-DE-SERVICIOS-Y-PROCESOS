import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (
                // Sockets y flujos de datos
                Socket socket = new Socket("localhost", 7000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in) // Dentro del try para cierre automático
        ) {
            System.out.println("Conectado al servidor");
            boolean login = false;

            // VALIDACIÓN DE CREDENCIALES
            String respuestaAutenticacion;
            while ((respuestaAutenticacion = in.readLine()) != null) {
                if (respuestaAutenticacion.equals("LOGIN_OK")) {
                    System.out.println("¡Registrado correctamente! Accediendo al sistema...");
                    login = true;
                    break;
                }
                else if (respuestaAutenticacion.equals("LOGIN_BAD")) {
                    System.out.println("Demasiados intentos incorrectos. El servidor ha rechazado la conexión. Vuelve a intentarlo mas tarde");
                    break;
                }
                else if (respuestaAutenticacion.equals("Usuario")) {
                    System.out.print("Introduce tu usuario: ");
                    out.println(sc.nextLine());
                }
                else if (respuestaAutenticacion.equals("password")) {
                    System.out.print("Introduce tu contraseña: ");
                    out.println(sc.nextLine());
                }
                else if (respuestaAutenticacion.equals("CREDENTIALS_BAD")) {
                    System.out.println("Credenciales incorrectas. Vuelve a intentarlo de nuevo.");
                }
            }

            // BUCLE DE OPERACIONES
            String opcion = "";
            while (!opcion.equals("5") && login) {
                System.out.println("\n--- Menú Principal ---" +
                        "\n1. Sumar" +
                        "\n2. Contador" +
                        "\n3. Invierte" +
                        "\n4. EsPrimo" +
                        "\n5. Salir");

                System.out.print("Introduce una opción: ");

                opcion = sc.nextLine();
                out.println(opcion); // Transmitimos la decisión

                // Switch para simplificar la lógica
                switch (opcion) {
                    case "1":
                        if ("n1_sumar".equals(in.readLine())) {
                            System.out.print("Introduce el primer número: ");
                            out.println(sc.nextLine());
                        }
                        if ("n2_sumar".equals(in.readLine())) {
                            System.out.print("Introduce el segundo número: ");
                            out.println(sc.nextLine());
                        }
                        System.out.println("Servidor: " + in.readLine());
                        break;

                    case "2":
                        if ("palabra_contador".equals(in.readLine())) {
                            System.out.print("Introduce la palabra para contar sus vocales: ");
                            out.println(sc.nextLine());
                        }
                        System.out.println("Servidor: son " + in.readLine() + " vocales");
                        break;

                    case "3":
                        if ("texto_invertir".equals(in.readLine())) {
                            System.out.println("Introduce el texto para invertirlo: ");
                            out.println(sc.nextLine());
                        }
                        System.out.println("Servidor: Texto invertido es: " + in.readLine());
                        break;

                    case "4":
                        if ("numero_primo".equals(in.readLine())) {
                            System.out.println("Introduce un número para comprobar si es primo: ");
                            out.println(sc.nextLine());
                        }
                        System.out.println(in.readLine());
                        break;

                    case "5":
                        break;// Se termina el menú

                    default:
                        System.out.println("Opción no valida");
                        break;
                }
            }
            System.out.println("Desconectado del servidor.");

        } catch (UnknownHostException e) {
            System.err.println("Error: Servidor desconocido.");
        } catch (IOException e) {
            System.err.println("Error de conexión. ¿Está el servidor encendido?");
        }
    }
}