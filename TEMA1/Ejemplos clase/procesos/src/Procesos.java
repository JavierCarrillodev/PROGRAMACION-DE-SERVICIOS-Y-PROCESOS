public class Procesos {
    public static void main(String[] args) {
        //Procesos
        //ejemplo1();
        //ejemplo2();

        //Hilo
        ejemplo3();
        //ejemplo4();
    }
        private static void ejemplo1() {
            try {
                ProcessBuilder pb = new ProcessBuilder("virtualbox");
                Process proceso = pb.start();
                System.out.println("Iniciando Virtualbox");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            private static void ejemplo2() {
                try {
                    //Lanzar una aplicacion grafica (ej:gedit)
                    ProcessBuilder pb = new ProcessBuilder("firefox");
                    Process proceso = pb.start();
                    System.out.println("Se ha lanzado firefox. Cierra la ventana para continuar...");

                    //Eperar a que se cierre la aplicacion
                    int exitCode = proceso.waitFor();
                    System.out.println("firefox se cerró.Código de salida: " + exitCode);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    private static void ejemplo3(){
        try {
            System.out.println("Ejecución secuencial");
            long inicioSec = System.currentTimeMillis();

            new Contador("Contador 1").secuencialRun();
            new Contador("Contador 2").secuencialRun();

            long finSec = System.currentTimeMillis();
            System.out.println("Tiempo total secuencial: " + (finSec-inicioSec) / 1000.0 + " segundos\n");

            System.out.println("Ejecución concurrente con hilos");
//            long inicioCon = System
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
