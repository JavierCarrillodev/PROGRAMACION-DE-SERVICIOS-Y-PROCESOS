# Práctica 1.2: Trivia en Red (Sockets TCP Java)

Esta es la resolución de la práctica para programar un juego de Trivial multijugador con arquitectura cliente-servidor usando Sockets y manejo de hilos (Threads) en Java.

## Arquitectura del Sistema

El proyecto funciona de manera centralizada. El servidor se encarga de todo el peso lógico de la partida, mientras que los clientes actúan únicamente como terminales de lectura y escritura para los jugadores.

```text
┌─────────────┐       TCP (5000)      ┌─────────────────┐
│  Jugador 1   │ <──────────────────> │                 │
├─────────────┤                       │                 │
│  Jugador 2   │ <──────────────────> │    Servidor     │
├─────────────┤                       │  (GameManager)  │
│  Jugador N   │ <──────────────────> │                 │
│  (máx. 10)   │                       │                 │
└─────────────┘                       └─────────────────┘
```

El servidor principal abre los puertos de escucha y delega cada conexión de cliente entrante a un hilo separado. Todo esto confluye finalmente en el gestor general del juego (`GameManager`), que es el que se encarga de sincronizar y mandar la información de forma masiva en cada ronda.

## Estructura de Archivos y Clases

Todo el código y archivos fuente se encuentran encapsulados dentro de la carpeta `src`. La distribución del programa es la siguiente:

```text
Proyecto_Trivial/
├── src/
│   ├── Servidor.java          # Clase principal que levanta el socket inicial y espera el comando START.
│   ├── Cliente.java           # Clase ejecutable que utilizarán los usuarios para conectarse.
│   ├── ClienteHandler.java    # Hilo individual del lado del servidor creado para atender a cada cliente sin que se formen cuellos de botella.
│   ├── GameManager.java       # El núcleo de la partida. Tiene las rutinas del temporizador, correcciones de respuestas y listado de rankings.
│   ├── Pregunta.java          # Un modelo de objeto clásico para encapsular el enunciado, alternativas (A, B, C, D) y la resolución.
│   └── ReceptorMensajes.java  # Hilo auxiliar que se arranca en paralelo dentro del Cliente para que el flujo de lectura no bloquee el teclado de escritura.
```

## Protocolo de Red (Comunicación)

Para estandarizar de forma muy limpia lo que la consola del Servidor y las de los Clientes se transmiten por el buffer de conexión, he diseñado un protocolo propio separando las palabras reservadas de sus valores correspondientes.

| Dirección Flujo       | Sintaxis del Mensaje   | Cometido lógico en el código               |
|-----------------------|------------------------|--------------------------------------------|
| Cliente -> Servidor   | [Texto Normal]         | Al conectarse da acceso y guarda el Nick.  |
| Cliente -> Servidor   | `RESPUESTA|letra`      | Comando obligatorio para puntuar (ej: `RESPUESTA|c`).|
| Servidor -> Cliente   | [Textos Largos]        | Transmisión de enunciados, rankings y notas.|

## Puesta en Marcha (Pasos de Ejecución)

### Requisitos previos:
- Utilizar un kit de desarrollo Java (JDK 8 o en adelante).
- Levantar todos los archivos en una red local o usar loopback (`localhost`) si estás probando varias ventanas en tu propio PC.

### Proceso de inicio:
1. **Preparar el terreno (Host)**: Lo primero es ejecutar en la raíz de src el archivo `Servidor.java`. La terminal indicará que está preparado de cara al público en el puerto 5000.
2. **Entrada de los jugadores**: Después, debes empezar a lanzar los archivos de `Cliente.java`, recordando que hay un tope de 10 como estipulan las reglas. Justo al iniciar cada cliente, saltará un mensaje para registrar un nombre de usuario en la base del servidor.
3. **El pistoletazo de salida**: Tras verificar que toda la gente está dentro de la sala de espera, entra en la terminal central originada en el primer paso y escribe el comando `START`. Esto desencadenará automáticamente el inicio de la primera ronda temporal.

## Mecánicas y Reglas Oficiales

- Durante la partida se volcarán progresivamente hasta 5 test seguidos sobre sistemas, programación y redes.
- En cuanto el enunciado acabe impreso en las pantallas de los usuarios, se empieza a descontar por detrás un contador de tiempo real de 15 segundos.
- Tienes exactamente ese margen temporal para mandar la instrucción, la cual debe estar formateada bajo el guión del sistema `RESPUESTA|LETRA`.
- Solamente computa el primer intento enviado en este segmento. Si te precipitas no hay oportunidad de arreglarlo.
- Un acierto vale de pleno derecho 1 punto entero. Una respuesta fallada, responder otra cosa al azar, no seguir el protocolo o enviar el comando fuera del segundero cuenta automáticamente para 0 puntos.
- Al término del tiempo límite verás devuelto un listado parcial visual listando cómo va el Top de personas de la sala. El proceso se clona hasta la ronda cinco, donde el Game Manager detiene el juego para estampar la clasificación general definitiva y hacer oficial quién es el ganador con mayor ratio de aciertos.
