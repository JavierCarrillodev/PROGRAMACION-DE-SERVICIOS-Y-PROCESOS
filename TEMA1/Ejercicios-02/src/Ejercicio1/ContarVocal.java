package Ejercicio1;

import java.io.*;

public class ContarVocal extends Thread{
    String ficheroLeer;
    String ficheroEscribir;
    int contadorVocal;
    char[] vocalesBuscadas;

    public ContarVocal(String ficheroLeer, String ficheroEscribir, char[] vocalesBuscadas) {
        this.ficheroLeer = ficheroLeer;
        this.ficheroEscribir = ficheroEscribir;
        this.contadorVocal = 0;
        this.vocalesBuscadas = vocalesBuscadas;
    }

    @Override
    public void run() {
        try {
            FileReader fr = new FileReader(ficheroLeer);
            int numCaracter;
            while ((numCaracter = fr.read()) != -1){
                for(char vocal: vocalesBuscadas){
                    if ((Character.toLowerCase((char) numCaracter)) == vocal) contadorVocal++;
                }
            }

            fr.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroEscribir));
            bw.write("La vocal " + this.vocalesBuscadas[0] + " aparece: " + contadorVocal + " veces" );

            bw.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
