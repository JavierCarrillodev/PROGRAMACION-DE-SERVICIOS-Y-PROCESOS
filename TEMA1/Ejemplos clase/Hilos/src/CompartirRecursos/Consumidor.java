package CompartirRecursos;

public class Consumidor  extends Thread{
    private Compartido recurso;
    public Consumidor(Compartido recurso){
        this.recurso = recurso;

    }
    @Override
    public void run(){
        for (int i = 0; i < 5 ; i++) {
            System.out.println("Producto leyó: " + recurso.getValor());
            try{
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }
}
