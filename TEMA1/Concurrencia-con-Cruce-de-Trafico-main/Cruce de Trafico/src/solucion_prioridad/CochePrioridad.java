package solucion_prioridad;

// Creamos la clase coche prioriad para poder hacer los hilos que ejeuten los procesos, cada instancia es un coche
public class CochePrioridad extends Thread {
    // Le asignamos los atributos
    private Object cuadrante1, cuadrante2;
    private int numC1, numC2;
    private int prioridad;

    // Y creamos un constructor
    public CochePrioridad(Object c1, int n1, Object c2, int n2, String nombre, int prioridad) {
        this.cuadrante1 = c1;
        this.cuadrante2 = c2;
        this.numC1 = n1;
        this.numC2 = n2;
        this.setName(nombre);
        this.prioridad = prioridad;
    }

    // Creamos el metoto run, run es el método que que se ejecuta cuando hacemos el .start
    @Override
    public void run() {
        try {
            // Los coches de menor prioridad esperan un poco para dejar pasar al de mayor prioridad
            // Esto hace que a la prioridad le sumemos segundos, por ejemplo la prioridad 1, es la mas alta, restamos 1 y lo mutiplicamos por le timepo esto hace que el el timepo de espera sea 0, entonces sale de inmediato. Con la prioridad 2 hace que espere 1 segundo, y asi con las demas.
            Thread.sleep((prioridad - 1) * 1000);

            // Con el synchronized hacemos que cuando cuando llegan los coches a la vez, el synchronized bloquea la otra parte del codigo para que este se ejute cuando este se ejecuta ya deja libre la otra parte.
            // Al hacer esto bloqueamos el acceso al cuadrante entre paréntesis, es decir, ningún otro puede usar el cuadrante 1, pero si otros cuadrantes, como el cuadrante 2 de abajo, pero este no lo usa porque esta dormido.
            synchronized (cuadrante1) {
                System.out.println(getName() + " ocupa cuadrante " + numC1);
                Thread.sleep(200);

                System.out.println(getName() + " intenta entrar a cuadrante " + numC2);

                // Aqui pasa lo mismo que arriba, pero aqui ninguno puede usar el cuandrante 2, pero si los demas como el cuadrante 1. Pero no lo usan porque estan dormidos.
                synchronized (cuadrante2) {
                    System.out.println(getName() + " ocupa cuadrante " + numC2 + " y cruza el cruce\n");
                    Thread.sleep(200);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
