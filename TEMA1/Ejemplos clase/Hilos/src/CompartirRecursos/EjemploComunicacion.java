package CompartirRecursos;

public class EjemploComunicacion {
    public static void main(String[] args) {
//        Compartido recurso = new Compartido();
//        Productor p = new Productor(recurso);
//        Consumidor c = new Consumidor(recurso);
//        p.start();
//        c.start();
        EjemploSinc();

    }
    public static void EjemploSinc(){
        Recurso recurso = new Recurso();
        new ProductorCom(recurso).start();
        new ConsumidorCom(recurso).start();
    }
}
