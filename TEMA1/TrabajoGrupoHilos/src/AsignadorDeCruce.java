import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

class AsignadorDeCruce {
    private final int capacidadMaxima;
    private int ocupados = 0;  // vehículos en el cruce
    private final Lock lock = new ReentrantLock();
    private final Condition puedeEntrar = lock.newCondition();
    private final List<String> espera = new ArrayList<>();

    public AsignadorDeCruce(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    // Solicita entrar al cruce (simula algoritmo del banquero)
    public void solicitarEntrada(String vehiculo) throws InterruptedException {
        lock.lock();
        try {
            espera.add(vehiculo);
            while (!estadoSeguro()) {
                System.out.println(vehiculo + " espera para entrar al cruce...");
                puedeEntrar.await();
            }
            ocupados++;
            espera.remove(vehiculo);
            System.out.println(vehiculo + " entra al cruce. Ocupados: " + ocupados);
        } finally {
            lock.unlock();
        }
    }

    // Liberar el cruce
    public void salir(String vehiculo) {
        lock.lock();
        try {
            ocupados--;
            System.out.println(vehiculo + " ha salido del cruce. Ocupados: " + ocupados);
            puedeEntrar.signalAll();  // despierta a los vehículos en espera
        } finally {
            lock.unlock();
        }
    }

    // Estado seguro simplificado: no se puede superar la capacidad
    private boolean estadoSeguro() {
        return ocupados < capacidadMaxima;
    }
}
