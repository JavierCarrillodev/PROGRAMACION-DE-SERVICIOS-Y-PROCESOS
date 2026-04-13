package solucion_semaforo;

public class SolucionSemaforo {
    public static void main(String[] args) {
        // Semáforo que permite pasar a los coches si no interfieren
        GestorSemaforo gestorSemaforo = new GestorSemaforo();

        // Cada hilo representa un coche, pasamos por constructor el mismo gestorSemaforo
        CocheSemaforo cocheB = new CocheSemaforo( 2, 3, "Coche B", gestorSemaforo);
        CocheSemaforo cocheC = new CocheSemaforo( 3, 4, "Coche C", gestorSemaforo);
        CocheSemaforo cocheD = new CocheSemaforo( 4, 1, "Coche D", gestorSemaforo);
        CocheSemaforo cocheA = new CocheSemaforo( 1, 2, "Coche A", gestorSemaforo);

        // Iniciamos los hilos para que llamen al método .run()
        cocheA.start();
        cocheB.start();
        cocheC.start();
        cocheD.start();

        // Espera a que cada hilo termine, por si hay alguna excepción
        try {
            cocheA.join();
            cocheB.join();
            cocheC.join();
            cocheD.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        // Si no salta ninguna excepcion salta el mensaje de que todo ha funcionado correctamente
        System.out.println("\nLos coches han cruzado correctamente");
    }
}