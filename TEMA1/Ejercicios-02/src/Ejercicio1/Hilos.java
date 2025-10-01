package Ejercicio1;

public class Hilos extends Thread{
    public static void main(String[] args) {


            Thread hiloA = new Hilos();
            Thread hiloE = new Hilos();
            Thread hiloI = new Hilos();
            Thread hiloO = new Hilos();
            Thread hiloU = new Hilos();

            hiloA.start();
            hiloE.start();
            hiloI.start();
            hiloO.start();
            hiloU.start();


    }
}
