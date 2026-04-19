import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){
        try(
                Socket socket = new Socket("localhost",1234);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new PrintWriter(socket.getOutputStream()), true);
                Scanner sc = new Scanner(System.in);
        ){
            System.out.println("Llegada al restaurante correctamente");

            int option;
            ArrayList<Double> preciosPlatos = new ArrayList<>();
            do {
                System.out.println("------ Carta ------");
                System.out.println("1.Caracoles ----4€");
                System.out.println("2.Spaguettis----12");
                System.out.println("3.Bocadillo calamares-----6€");
                System.out.println("4.Tostada de jamon con tomate-----2,5€");
                System.out.println("5.Risotto-----14€");
                System.out.println("6.La multa jefe");
                System.out.println("7.Pagar cuenta");
                option = sc.nextInt();

                double total = 0;

                switch (option){
                    case 1:
                        out.println("Dos tapas de caracoles");
                        preciosPlatos.add(4.0);
                        break;
                    case 2:
                        out.println("Spaguettis 2 platos");
                        preciosPlatos.add(12.0);
                        break;
                    case 3:
                        out.println("1 Bocadillo calamares");
                        preciosPlatos.add(6.0);
                        break;
                    case 4:
                        out.println("1 Tostada de jamon con tomate");
                        preciosPlatos.add(2.5);
                        break;
                    case 5:
                        out.println("1 Risotto");
                        preciosPlatos.add(14.0);
                        break;
                    case 6:
                        if (preciosPlatos.isEmpty()){
                            System.out.println("Todavia no habeis pedido nada");
                        }
                        for (double precio:preciosPlatos){
                            total = total + precio;
                        }
                        System.out.println("El total de la cuenta es: " + total + "€");
                        break;
                    case 7:
                        double importe = 0;
                        for (double precio:preciosPlatos){
                            total = total + precio;
                        }
                        do {
                            System.out.println("Introduce el importe de la cuenta que son: " + total);
                            importe = sc.nextDouble();
                            if (importe > total){
                                System.out.println("Me estas dando mas rey");
                            } else if (importe < total) {
                                System.out.println("No me vas a engañar");
                            }else {
                                System.out.println("Muchas gracias por venir");
                            }

                        }while (importe !=total);
                        break;
                    default:
                        System.out.println("Opcion no disponible");
                }

            }while (option != 7);


        }catch (UnknownHostException e){
            System.err.println("Error:Servidor desconocido");
        }catch (IOException e){
            System.err.println("Error de conexion. Servidor no encendido");
        }


    }
}
