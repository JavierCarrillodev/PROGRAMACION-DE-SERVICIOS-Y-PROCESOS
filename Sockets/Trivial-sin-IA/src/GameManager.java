import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameManager {
    private ArrayList<ClienteHandler> clientes = new ArrayList<>();
    private static volatile boolean rondaAbierta = false;
    private ArrayList<Pregunta> preguntas = new ArrayList<>();

    public GameManager(ArrayList<ClienteHandler> clientes) {
        this.clientes = clientes;
        // Pregunta 1
        this.preguntas.add(new Pregunta("¿Qué lenguaje se utiliza principalmente para gestionar y consultar bases de datos relacionales?", "a) XML", "b) SQL", "c) JSON", "d) PHP", "b"));

        // Pregunta 2
        this.preguntas.add(new Pregunta("¿Qué comando de Git se utiliza para enviar los cambios locales a un repositorio remoto (como GitHub)?", "a) git pull", "b) git commit", "c) git push", "d) git clone", "c"));

        // Pregunta 3
        this.preguntas.add(new Pregunta("¿Qué es una dirección IP?", "a) Un tipo de cable de fibra óptica.", "b) Un identificador único numérico para un dispositivo en una red.", "c) Un programa antivirus para navegar por internet.", "d) La contraseña por defecto del router.", "b"));

        // Pregunta 4
        this.preguntas.add(new Pregunta("¿Cuál de los siguientes sistemas operativos es de código abierto (Open Source)?", "a) Windows 11", "b) macOS", "c) iOS", "d) Linux", "d"));

        // Pregunta 5
        this.preguntas.add(new Pregunta("En seguridad informática, ¿en qué consiste un ataque de 'Phishing'?", "a) Infectar el PC con un virus de borrado masivo.", "b) Sobrecargar un servidor para que se caiga (DDoS).", "c) Engañar al usuario haciéndose pasar por una entidad de confianza para robar sus datos.", "d) Hackear la contraseña del Wi-Fi por fuerza bruta.", "c"));
    }

    public void iniciarPartida() {
        try {
            System.out.println("Partida iniciada");
            enviarTodos("=== LA PARTIDA HA COMENZADO ===");

            int numPregunta = 1;
            for (Pregunta p : preguntas) {
                enviarTodos("\n--- Pregunta " + numPregunta + " de " + preguntas.size() + " ---");
                enviarTodos(extraerPregunta(p));
                enviarTodos("Tienes 15 segundos para responder. Formato: RESPUESTA|letra");
                rondaAbierta = true;
                Thread.sleep(15000);
                rondaAbierta = false;
                corregirRespuestas(p.getRespuestaCorrecta());
                // Ranking tras cada pregunta
                mostrarRanking(false);
                limpiarRespuestas();
                numPregunta++;
            }

            // Ranking final con ganador
            enviarTodos("\n========== PARTIDA FINALIZADA ==========");
            mostrarRanking(true);

        } catch (Exception e) {
            System.out.println("Error en iniciar partida: " + e.getMessage());
        }
    }

    public void mostrarRanking(boolean esFinal) {
        // Ordenado por puntos de mayor a menor
        Collections.sort(clientes, new Comparator<ClienteHandler>() {
            @Override
            public int compare(ClienteHandler a, ClienteHandler b) {
                return b.getPuntos() - a.getPuntos();
            }
        });

        String titulo;
        if (esFinal) {
            titulo = "=== RANKING FINAL ===";
        } else {
            titulo = "=== RANKING PARCIAL ===";
        }

        String ranking = titulo + "\n";
        for (int i = 0; i < clientes.size(); i++) {
            ClienteHandler cl = clientes.get(i);
            ranking += (i + 1) + "º - " + cl.getNick() + ": " + cl.getPuntos() + " puntos\n";
        }

        if (esFinal) {
            // Anunciar ganador
            ClienteHandler ganador = clientes.get(0);
            ranking += "GANADOR: " + ganador.getNick() + " con " + ganador.getPuntos() + " puntos!";
        }

        // Mostrar en consola del servidor
        System.out.println(ranking);

        // Enviar a todos los clientes
        enviarTodos(ranking);
    }

    public void limpiarRespuestas() {
        for (ClienteHandler cl : clientes) {
            cl.limpiaRespuesta();
        }
    }

    public void enviarTodos(String msg) {
        for (ClienteHandler cl : clientes) {
            cl.enviarMensaje(msg);
        }
    }

    public void corregirRespuestas(String sol) {
        for (ClienteHandler cl : clientes) {
            cl.corregirRespuesta(sol);
        }
    }

    public String extraerPregunta(Pregunta p) {
        return p.getEnunciado() + "\n  A: " + p.getRespuestaA()
                + "\n  B: " + p.getRespuestaB()
                + "\n  C: " + p.getRespuestaC()
                + "\n  D: " + p.getRespuestaD();
    }

    public static boolean isRondaAbierta() {
        return rondaAbierta;
    }

    public ArrayList<ClienteHandler> getClientes() {
        return clientes;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }
}