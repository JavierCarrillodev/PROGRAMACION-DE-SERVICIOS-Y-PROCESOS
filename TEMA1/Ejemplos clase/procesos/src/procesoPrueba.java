public class procesoPrueba {
    public static void main(String[] args) {
        try{

            ProcessBuilder pb = new ProcessBuilder("gedit");
            Process proceso = pb.start();

            int exitCode = proceso.waitFor();
            System.out.println("Proceso terminado con codigo " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
