package solucion_semaforo;

public class GestorSemaforo {
    private boolean[] cuadrantes = new boolean[4]; // Tenenos un array que por defecto está libre

    public boolean puedePasar(int primerCuadrante, int segundoCuadrante) { // Método para comprobar si los cuadrantes a usar están libres
        // Si alguno de los cuadrantes está ocupado, no puede pasar
        return !cuadrantes[primerCuadrante - 1] && !cuadrantes[segundoCuadrante - 1];
    }

    // Método para entrar al cuadrante
    public synchronized void entrar(int primerCuadrante, int segundoCuadrante, String nombre) { // Solo un hilo/coche a la vez puede entrar
        while (!puedePasar(primerCuadrante, segundoCuadrante)) {
            try {
                wait(); // espera hasta que si no están disponibles
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Ocupa los cuadrantes
        cuadrantes[primerCuadrante - 1] = true;
        cuadrantes[segundoCuadrante - 1] = true;
        System.out.println(nombre + " pasa por el cruce (ocupa cuadrantes " + primerCuadrante + " y " + segundoCuadrante + ")");
    }

    // Método para salir del cuadrante
    public synchronized void salir(int primerCuadrante, int segundoCuadrante, String nombre) {
        // Liberamos los cuadrantes los cuadrantes
        cuadrantes[primerCuadrante - 1] = false;
        cuadrantes[segundoCuadrante - 1] = false;
        System.out.println(nombre + " sale del cruce (libera cuadrantes " + primerCuadrante + " y " + segundoCuadrante + ")\n");
        notifyAll(); // avisa a los demás coches para que comprueben si sus cuadrantes están libres
    }
}
