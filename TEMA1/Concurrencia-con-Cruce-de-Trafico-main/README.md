# 🚗 Concurrencia con Cruce de Tráfico: Prevención de Interbloqueos en Java

Este repositorio es un proyecto educativo de **Progamación de Servicios y Procesos** que aborda los desafíos críticos de la **programación concurrente**, centrándose en los conceptos de **Exclusión Mutua** y **Interbloqueo (Deadlock)**.

Hemos elegido el problema clásico del **Cruce de Tráfico** para simular estos problemas en **Java** y demostrar cómo las estrategias de sincronización son esenciales para el buen funcionamiento de un sistema.

---

## 1. 💡 Fundamentos Teóricos

El proyecto se basa en la necesidad de coordinar múltiples **hilos** que acceden a recursos compartidos.

### 1.1. Concurrencia

Definimos la concurrencia como la ejecución de múltiples tareas en un mismo período de tiempo. Permite iniciar y avanzar en distintas tareas de forma simultánea, pero introduce desafíos en la coordinación.

### 1.2. Exclusión Mutua (Mutual Exclusion)

Es el principio fundamental que garantiza que **solo un hilo/proceso a la vez** puede acceder a un recurso compartido.

* **Objetivo:** Evitar la **Pérdida de Integridad de los Datos** y la **Aparición de Fallos** por accesos conflictivos.
* **Advertencia:** Es un mecanismo esencial, pero una mala implementación puede causar interbloqueos.

### 1.3. Interbloqueo (Deadlock) 💀

Es una **situación permanente** donde dos o más hilos se bloquean mutuamente. Cada hilo posee un recurso que el otro necesita, creando un **ciclo de dependencia** que impide el avance.

El interbloqueo solo se produce si se cumplen las cuatro **condiciones de Coffman**:

| Condición | Causa |
| :--- | :--- |
| **Exclusión Mutua** | Los recursos están asignados de forma exclusiva. |
| **Retención y Espera** | El hilo retiene recursos mientras espera otros. |
| **Imposibilidad de Apropiación** | Los recursos no pueden ser forzados a dejar un hilo. |
| **Presencia de una Espera Circular** | Existe una cadena cíclica de dependencia entre los hilos. |

---

## 2. 🚦 Caso de Estudio: El Cruce de Tráfico

Simulamos una intersección de cuatro cuadrantes ($Q_1$ a $Q_4$), donde cada vehículo (hilo) necesita bloquear uno o varios cuadrantes (recursos) para cruzar.

### Simulación del Problema

El interbloqueo se simula cuando cuatro vehículos, uno por dirección y todos queriendo seguir recto, forman un ciclo de espera:

| Vehículo | Retiene (Recurso Poseído) | Espera (Recurso Necesario) |
| :--- | :--- | :--- |
| **Coche A** | $Q_1$ | $Q_2$ |
| **Coche B** | $Q_2$ | $Q_3$ |
| **Coche C** | $Q_3$ | $Q_4$ |
| **Coche D** | $Q_4$ | $Q_1$ |

**Resultado:** Ningún coche puede avanzar, estableciéndose un **interbloqueo real** al cumplirse las cuatro condiciones de Coffman.

---

## 3. ✨ Soluciones Implementadas en Java

Para evitar el interbloqueo, hemos implementado estrategias de **prevención** que rompen la condición de **Espera Circular** o **Retención y Espera**.

### 1. Prioridad por Vía (Rompiendo la Espera Circular)

Se asigna una **prioridad fija** a cada dirección (por ejemplo: Norte > Este > Sur > Oeste).

* **Mecanismo:** Antes de entrar, el coche verifica si hay vehículos de mayor prioridad esperando.
* **Efecto:** Siempre hay un coche que puede moverse. Esto rompe el ciclo de dependencia, ya que la prioridad asegura que la espera no sea circular.

### 2. Semáforos Reales (Control de Flujo)

Los vehículos esperan su turno para entrar al cruce siguiendo un **orden predefinido** (similar a un semáforo de un solo sentido).

* **Mecanismo:** Solo un vehículo entra al cruce a la vez, y al salir, cede el turno al siguiente.
* **Efecto:** Todos los vehículos esperan **fuera del cruce** hasta que es su turno, previniendo que retengan recursos mientras esperan otros (Negación de Retención y Espera) y evitando cualquier conflicto interno.

---

## 4. 💻 Estructura del Código

El proyecto está escrito en **Java** y utiliza las herramientas de concurrencia del lenguaje (como `synchronized` para implementar los recursos y la exclusión mutua).

## 📚 5. Documentación Adicional del Proyecto

Para una explicación más detallada sobre los conceptos, la metodología de resolución y las conclusiones del proyecto, consulte el documento completo:

[**Documentación Proyecto de Concurrencia**](https://docs.google.com/document/d/1EI47z6HOt3_Zy2Zg9NH6bYBkTg_qSvm5SEGb7ab-JHM/edit?usp=sharing)
