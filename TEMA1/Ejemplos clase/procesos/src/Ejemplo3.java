public class Ejemplo3 {
    public static void main(String[] args) {

    }
        public static void ejemplo3(){
            try {
                // ======================
                // EJECUCIÓN SECUENCIAL
                // ======================
                System.out.println("=== Ejecución secuencial ===");
                long inicioSec = System.currentTimeMillis();

//                new Contador("Contador 1").secuencial_run(); // .run() = mismo hilo
//                new Contador("Contador 2").secuencial_run();

                long finSec = System.currentTimeMillis();
                System.out.println("Tiempo total secuencial: " + (finSec - inicioSec) / 1000.0 + " segundos\n");


                // ======================
                // EJECUCIÓN CONCURRENTE
                // ======================
                System.out.println("=== Ejecución concurrente (con hilos) ===");
                long inicioCon = System.currentTimeMillis();

                Contador h1 = new Contador("Contador 1");
                Contador h2 = new Contador("Contador 2");

                h1.start();
                h2.start();

                // Esperar a que terminen ambos hilos
                h1.join();
                h2.join();

                long finCon = System.currentTimeMillis();
                System.out.println("Tiempo total concurrente: " + (finCon - inicioCon) / 1000.0 + " segundos");
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

}
