package Ejercicio3;

import java.io.BufferedReader;

import java.io.FileReader;

public class Ejer3 {
    public static void main(String[] args) {

        String fichero = "/home/javi/Documentos/Javi/2DAM/Programacion de Servicios y Procesos/TEMA1/Ejercicio/Ejercicios/src/Ejercicio3/fichero_entrada.txt";

        int contarLinea = 0;
        int info = 0;
        int error = 0;
        int warn = 0;

        try {

            BufferedReader br = new BufferedReader(new FileReader(fichero));

            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
                String[] partes = linea.split(";");

                if (partes.length > 0) {
                    String parte = partes[0].trim();
                    if (parte.equals("INFO")) {
                        info++;
                        contarLinea++;
                    } else if (parte.equals("ERROR")) {
                        error++;
                        contarLinea++;
                    } else if (parte.equals("WARN")) {
                        warn++;
                        contarLinea++;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Número total de registros: " + contarLinea);
        System.out.println("INFO: " + info);
        System.out.println("ERROR: " + error);
        System.out.println("WARN: " + warn);
    }
}
