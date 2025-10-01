package CompartirRecursos;

public class Recurso {
    private int valor;
    private boolean disponible = false;

    //Metodo para que el Productor escriba un valor
    public synchronized void producir(int nuevoValor) {
        while (disponible) { // si ya hay dato esperando, el productor espera
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        valor = nuevoValor;
        disponible = true;
        System.out.println("Productor produjo: " + valor);
        notify();
    }
// Metodo para que el Consumidor lea un valor
    public synchronized int consumir() {
        while (!disponible) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        disponible = false;
        System.out.println("Consumidor consumio: " + valor);
        notify(); // avisa el productor
        return valor;
    }
}
