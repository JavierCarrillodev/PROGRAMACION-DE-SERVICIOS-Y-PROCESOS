package solucion_semaforo;


public class CocheSemaforo extends Thread { // Extendemos de Thread, cada coche es un Hilo
    private int numC1, numC2; // Números para trabajar
    private GestorSemaforo gestor; // Necesitamos un objecto tipo GestorSemaforo

    // Creamos un constructor con toodas los parámetros, además del nombre del hilo/coche
    public CocheSemaforo(int n1, int n2, String nombre, GestorSemaforo gestor) {
        this.numC1 = n1;
        this.numC2 = n2;
        this.setName(nombre);
        this.gestor = gestor;
    }

    @Override
    public void run() {
        gestor.entrar(numC1, numC2, this.getName()); // En el gestor, se comprueba si es posible entrar, entramos o esperamos
        try {
            Thread.sleep(1000); // Dejamos tiempo para simular el curce
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gestor.salir(numC1, numC2, this.getName()); // Salimos dejando los cuadrantes libres de nuevo
    }
}