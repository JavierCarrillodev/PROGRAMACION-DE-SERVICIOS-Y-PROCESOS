package Ejercicio1;

/*Crear un programa que sea capaz de contar cuántas vocales hay en un fichero.
El programa padre debe lanzar cinco hilos hijo, donde cada uno de ellos se
ocupará de contar una vocal concreta (que puede ser minúscula o mayúscula).
Cada subproceso que cuenta vocales deberá dejar el resultado en un fichero. El
programa padre se ocupará de recuperar los resultados de los ficheros, sumar
todos los subtotales y mostrar el resultado final en pantalla.
*/
import java.io.BufferedReader;
import java.io.FileReader;

public class Ejer1 {
    public static void main(String[] args) {
        int contadorA = 0;
        int contadorE = 0;
        int contadorI = 0;
        int contadorO = 0;
        int contadorU = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/javi/Documentos/Javi/2DAM/Programacion de Servicios y Procesos/TEMA1/Ejercicios-02/src/Ejercicio1/FicheroConVocales.txt"));
            String linea;
            while ((linea = br.readLine()) !=null){
               String[] separarLineas = linea.split(" ");
                for (int i = 0; i < separarLineas.length ; i++) {
                    char[] separarPalabra = separarLineas[i].toCharArray();
                    for (int j = 0; j < separarPalabra.length; j++) {
                        separarPalabra[j] = Character.toLowerCase(separarPalabra[j]);
                        if (separarPalabra[j] == 'a' || separarPalabra[j] == 'á'){
                            contadorA++;
                        } else if (separarPalabra[j] == 'e' || separarPalabra[j] == 'é') {
                            contadorE++;
                        }else if (separarPalabra[j] == 'i' || separarPalabra[j] == 'í') {
                            contadorI++;
                        }else if (separarPalabra[j] == 'o' || separarPalabra[j] == 'ó') {
                            contadorO++;
                        }else if (separarPalabra[j] == 'u' || separarPalabra[j] == 'ú' || separarPalabra[j] == 'ü') {
                            contadorU++;
                        }
                    }
                }
            }
            System.out.println("contadorA = " + contadorA);
            System.out.println("contadorE = " + contadorE);
            System.out.println("contadorI = " + contadorI);
            System.out.println("contadorO = " + contadorO);
            System.out.println("contadorU = " + contadorU);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
