package solucion_prioridad;

public class SolucionPrioridad {
    public static void main(String[] args) {

        // Instanciamos los objetos, cada uno representa un cuadrante.
        // Esto hace que cuando un cuadrante entra en el cruce se inicie el synchronized y bloquee a los demas coches ese cuadrante
        Object c1 = new Object();
        Object c2 = new Object();
        Object c3 = new Object();
        Object c4 = new Object();

        // Aqui instanciamos la clase coche con cada uno de los coches(hilos).
        // A cada uno le pasamos por parametro los cuadrantes que ocupa, un nombre, y una prioridad.
        CochePrioridad cocheA = new CochePrioridad(c1, 1, c2, 2, "Coche A", 1);
        CochePrioridad cocheB = new CochePrioridad(c2, 2, c3, 3, "Coche B", 2);
        CochePrioridad cocheC = new CochePrioridad(c3, 3, c4, 4, "Coche C", 3);
        CochePrioridad cocheD = new CochePrioridad(c4, 4, c1, 1, "Coche D", 4);

        // Con el .start, esto lo que hace que crea el hilo y llamda al metodo run()
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

