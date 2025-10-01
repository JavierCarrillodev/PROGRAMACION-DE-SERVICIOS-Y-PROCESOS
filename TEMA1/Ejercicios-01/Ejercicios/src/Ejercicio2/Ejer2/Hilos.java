package Ejercicio2.Ejer2;
/*Crea 3 hilos con las siguientes tareas:
        -​ Hilo A: imprime “Descargando datos…” cada 2 segundos (3 veces).
        -​ Hilo B: imprime “Procesando…” cada 3 segundos (2 veces).
        -​ Hilo C: imprime “Guardando…” cada 1 segundo (5 veces).
El programa principal debe esperar que terminen los 3 hilos y muestren el siguiente
mensaje: “Todas las tareas finalizadas”.
*/
public class Hilos {
    public static void main(String[] args) {
        Thread hiloA = new HiloA();
        Thread hiloB = new HiloB();
        Thread hiloC = new HiloC();

        hiloA.start();
        hiloB.start();
        hiloC.start();

        try{
            hiloA.join();
            hiloB.join();
            hiloC.join();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Todas las tareas finalizadas.");
    }
   static class HiloA extends Thread{
        public void run(){
            for (int i = 0; i < 3 ; i++) {
                System.out.println("Descargando datos...");
                try{
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class HiloB extends Thread{
        public void run(){
            for (int i = 0; i < 2 ; i++) {
                System.out.println("Procesando...");
                try{
                    Thread.sleep(3000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    static class HiloC extends Thread{
        public void run(){
            for (int i = 0; i < 5 ; i++) {
                System.out.println("Guardando...");
                try{
                    Thread.sleep(1000);

                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
