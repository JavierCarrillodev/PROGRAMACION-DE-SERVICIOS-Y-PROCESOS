public class Main {
    public static void main(String[] args) {
        AsignadorDeCruce asignador = new AsignadorDeCruce(2); // solo 2 vehículos a la vez

        VehiculoBanquero vNorte = new VehiculoBanquero("Vehículo Norte", asignador);
        VehiculoBanquero vSur = new VehiculoBanquero("Vehículo Sur", asignador);
        VehiculoBanquero vEste = new VehiculoBanquero("Vehículo Este", asignador);
        VehiculoBanquero vOeste = new VehiculoBanquero("Vehículo Oeste", asignador);

        vNorte.start();
        vSur.start();
        vEste.start();
        vOeste.start();
    }
}
