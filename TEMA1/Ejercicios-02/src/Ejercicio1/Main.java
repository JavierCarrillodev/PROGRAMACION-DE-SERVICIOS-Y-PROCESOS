package Ejercicio1;

/*Crear un programa que sea capaz de contar cuántas vocales hay en un fichero.
El programa padre debe lanzar cinco hilos hijo, donde cada uno de ellos se
ocupará de contar una vocal concreta (que puede ser minúscula o mayúscula).
Cada subproceso que cuenta vocales deberá dejar el resultado en un fichero. El
programa padre se ocupará de recuperar los resultados de los ficheros, sumar
todos los subtotales y mostrar el resultado final en pantalla.
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        String ficheroLeer = "Ejercicios-02/src/FicheroConVocales.txt";

        String[] ficherosEscribir = {
                "Ejercicios-02/src/VocalesA.txt",
                "Ejercicios-02/src/VocalesE.txt",
                "Ejercicios-02/src/VocalesI.txt",
                "Ejercicios-02/src/VocalesO.txt",
                "Ejercicios-02/src/VocalesU.txt"
        };

        char[] vocalbuscadaA = {'a', 'á'};
        char[] vocalbuscadaE = {'e', 'é'};
        char[] vocalbuscadaI = {'i', 'í'};
        char[] vocalbuscadaO = {'o', 'ó'};
        char[] vocalbuscadaU = {'u', 'ú', 'ü'};

        Thread hiloA = new ContarVocal(ficheroLeer, ficherosEscribir[0], vocalbuscadaA);
        Thread hiloE = new ContarVocal(ficheroLeer, ficherosEscribir[1], vocalbuscadaE);
        Thread hiloI = new ContarVocal(ficheroLeer, ficherosEscribir[2], vocalbuscadaI);
        Thread hiloO = new ContarVocal(ficheroLeer, ficherosEscribir[3], vocalbuscadaO);
        Thread hiloU = new ContarVocal(ficheroLeer, ficherosEscribir[4], vocalbuscadaU);

        hiloA.start();
        hiloE.start();
        hiloI.start();
        hiloO.start();
        hiloU.start();

        try {
            hiloA.join();
            hiloE.join();
            hiloI.join();
            hiloO.join();
            hiloU.join();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < 5 ; i++) {
                BufferedReader br = new BufferedReader(new FileReader(ficherosEscribir[i]));
                String linea;
                while ((linea = br.readLine()) !=null){
                    System.out.println(linea);

                }
                br.close();
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
