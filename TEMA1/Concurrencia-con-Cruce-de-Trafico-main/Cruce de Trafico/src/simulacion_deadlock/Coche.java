package simulacion_deadlock;

// Creamos esta clase para simular los hilos
public class Coche extends Thread {
    // Le ponemos objetos para poder utilizar sychronized
    private Object cuadrante1, cuadrante2;
    // También tenemos los números para poder imprimir más facil
    private int numC1, numC2;

    // Creamos un constructor con todos los parámetros y además el nombre del hilo / coche para identificarlo mejor
    public Coche(Object c1, int n1, Object c2, int n2, String nombre) {
        this.cuadrante1 = c1;
        this.cuadrante2 = c2;
        this.numC1 = n1;
        this.numC2 = n2;
        this.setName(nombre);
    }

    @Override
    // Método que se ejecuta al hacer .start
    public void run() {

        // Ponemos while true para simular atasco
        while (true) {
            try {
                // Solo puede ocuparse una vez este objeto
                synchronized (cuadrante1) {
                    System.out.println(getName() + " ocupa cuadrante " + numC1);
                    Thread.sleep(500);


                    // Aviso antes de intentar tomar el segundo cuadrante
                    System.out.println(getName() + " intenta entrar a cuadrante " + numC2);

                    // Ahora intenta utilzar el segundo, pero si está ocupado tiene que esperar hasta que se libere
                    synchronized (cuadrante2) {
                        System.out.println(getName() + " ocupa cuadrante " + numC2 + " y cruza el cruce");
                        Thread.sleep(500);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
