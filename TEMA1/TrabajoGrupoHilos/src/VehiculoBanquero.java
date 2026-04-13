class VehiculoBanquero extends Thread {
    private final AsignadorDeCruce asignador;

    public VehiculoBanquero(String nombre, AsignadorDeCruce asignador) {
        super(nombre);
        this.asignador = asignador;
    }

    @Override
    public void run() {
        try {
            asignador.solicitarEntrada(getName());
            // Simula tiempo de cruzar
            Thread.sleep(500);
            asignador.salir(getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
