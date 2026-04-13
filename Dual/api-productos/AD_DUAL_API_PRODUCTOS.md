# Actividad DUAL 1 - Documentación del Proyecto
**Proyecto:** API de Gestión de Productos (`api-productos`)

---

## RA2 - Gestión de Bases de Datos Relacionales

### 1. Análisis del uso de bases de datos en el entorno profesional

**Identificación del Proceso:**
En cualquier empresa comercial, uno de los procesos más críticos es la **Gestión del Catálogo de Productos y el Inventario**. Este proceso implica almacenar un registro detallado de cada artículo, su precio, existencias y categoría.

**Gestión Actual (Sin BD Relacional):**
En empresas que aún no se han digitalizado completamente, estos datos se suelen gestionar mediante hojas de cálculo (como Excel) o documentos de texto dispersos. Esto genera graves problemas:
- **Redundancia:** Los datos de un producto se copian en múltiples hojas (ventas, almacén, etc.).
- **Inconsistencia:** Si cambia el precio de un producto y no se actualiza en todas las hojas, se generarán errores de facturación.
- **Falta de Concurrencia:** Dos empleados no pueden editar el mismo archivo de Excel simultáneamente sin riesgo de sobrescribir datos.

**Ventajas de usar una Base de Datos Relacional:**
La implementación de una BD relacional (como MySQL o un sistema embebido como H2) soluciona estos problemas estructurales:
1. **Integridad Referencial:** Los datos se estructuran de forma lógica (Normalización).
2. **Consistencia (Fuente única de verdad):** Si se actualiza el precio de un producto, el cambio es visible instantáneamente para toda la empresa.
3. **Seguridad y Accesos:** Permite definir qué usuarios pueden ver o editar ciertos datos.

---

### 2. Creación de una base de datos de práctica

**Diseño y Elección de Tecnología:**
Para esta práctica, hemos simulado la gestión de productos mediante una API REST en **Java (Spring Boot)**, utilizando **H2 Database**, un SGBD relacional embebido que se ejecuta en memoria y es ideal para el desarrollo y pruebas ágiles, cumpliendo la directiva de uso de BD locales/embebidas.

**Conexión a la Base de Datos:**
La conexión se establece de forma transparente mediante Spring Data JPA. La configuración en `application.yml` es la siguiente:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: Javi
    password:
```

**Operaciones CRUD y Explotación de Datos:**
Hemos desarrollado una aplicación completa (`ProductoController.java`) que permite realizar todas las operaciones básicas sobre la base de datos:
- **CREATE (Inserción):** Método POST para introducir nuevos productos.
- **READ (Consulta):** Métodos GET para listar todos los productos o buscar por ID.
- **UPDATE (Modificación):** Método PUT para actualizar precios o datos de un producto existente.
- **DELETE (Eliminación):** Método DELETE para borrar productos del catálogo.

**Aplicación con Inicio de Sesión:**
La API cuenta con medidas de seguridad robustas implementadas mediante **Spring Security** (`SecurityConfig.java`). Se ha requerido autenticación básica para cualquier operación de modificación del inventario.

```java
@Bean
public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();
    return new InMemoryUserDetailsManager(admin);
}
```
*Solo el usuario `admin` autenticado puede crear o modificar productos. Las rutas GET (lectura) permanecen públicas.*

---

### 3. Simulación de transacciones y reflexión sobre integridad

En este ecosistema, la gestión transaccional es clave. Operaciones críticas (como procesar una orden que actualiza múltiples registros) no pueden permitirse quedar a medias.

**Mecanismo Transaccional (COMMIT y ROLLBACK):**
En Spring Data JPA, las operaciones en la base de datos están envueltas en un contexto transaccional implícito (`@Transactional`).
A nivel de base de datos se traduce en un guion estructurado así:
```sql
START TRANSACTION;
INSERT INTO productos (nombre, precio) VALUES ('Ordenador Portátil', 850.50);
-- Si todo va bien:
COMMIT;
-- Si ocurre una excepción de integridad (e.g. campo nulo):
ROLLBACK;
```

**Reflexión en el Entorno Real:**
Si una aplicación de comercio electrónico (que consume esta API) registra una venta, debe descontar el stock e ingresar el registro de la factura. Si falla la facturación, el mecanismo de `ROLLBACK` asegura que el stock previamente descontado se recupere, manteniendo la **integridad absoluta** y previniendo pérdidas económicas.

---

## RA5 - Análisis de uso del formato XML en el entorno laboral

### 1. Análisis de uso del formato XML en el entorno laboral

Aunque este proyecto destaca el uso de JSON para la comunicación REST, el formato **XML** sigue teniendo un papel insustituible a nivel corporativo e industrial.

**Contextos de uso en una empresa:**
- **Configuración de Proyectos:** El archivo `pom.xml` de nuestro propio proyecto `api-productos` es un documento XML real que gestiona y vertebra todas las dependencias del ciclo de vida del software.
- **Facturación Electrónica (FacturaE):** Estándar obligatorio exigido por las administraciones públicas y grandes compañías.
- **Intercambio B2B (EDI):** Para coordinar inventarios de productos y envíos entre el sistema de la empresa y proveedores externos de mensajería (SEUR, DHL, etc.).

**Comparativa Tecnológica:**

| Característica | XML | JSON | CSV | SQL |
| :--- | :--- | :--- | :--- | :--- |
| **Estructura** | Jerárquica, verbosa usando etiquetas `<tag>` | Jerárquica y ligera (Clave-Valor) | Plana, tabular | Relacional (Tablas) |
| **Legibilidad** | Muy descriptiva (soporta atributos de metadatos) | Directa y compacta | Difícil de leer sin contexto | Sintaxis estricta de consulta |
| **Validación** | Fuerte mediante esquemas XSD / DTD | Esquemas JSON (opcionales y menos estándar)| Nula | Fuerte (Esquemas propios DB) |
| **Caso ideal** | Documentos formales y complejos (Facturas, contratos B2B) | APIs REST (ej. `api-productos`), Web y Móviles | Exportaciones de bases de datos masivas (Excel) | Persistencia de datos permanente y relacional |

---

### 2. Creación y manipulación de documentos XML

En el ecosistema Java de nuestra empresa simulada, la interacción con documentos XML para importaciones de productos masivas suele abordarse mediante los parsers **DOM** (Document Object Model) o **SAX** (Simple API for XML).

**Ventajas según el parser:**
- **DOM:** Carga todo el documento XML en memoria, creando un árbol de nodos (`Node`, `Element`). Resulta útil si necesitamos acceder aleatoriamente al archivo de configuración o modificar atributos concretos de un producto importado antes de inyectarlo por la API.
- **SAX:** Es impulsado por eventos y no carga el documento completo, consumiendo ínfima memoria. Es crítico si estamos importando un archivo XML de producto provisto por un gran mayorista de varios GB de tamaño.

**Fragmento Conceptual (DOM en Java):**
```java
// Cargando un inventario XML (documento físico)
File file = new File("inventario_productos.xml");
DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
Document doc = dbf.newDocumentBuilder().parse(file);
doc.getDocumentElement().normalize();

// Modificando un nodo
NodeList nodeList = doc.getElementsByTagName("producto");
for (int i = 0; i < nodeList.getLength(); i++) {
    Element element = (Element) nodeList.item(i);
    if(element.getAttribute("id").equals("101")) {
        element.getElementsByTagName("precio").item(0).setTextContent("599.99");
    }
}
```

### 3. Organización de Colecciones XML

De la misma manera que las bases de datos relacionales organizan su información en **Esquemas** y **Tablas**, las Bases de Datos Nativas XML (como eXist-db) o un Gestor de Archivos Físicos se basan en **Colecciones lógicas**.

**Gestión y Mantenimiento:**
Para una empresa, aglutinar todos los XML generados (ej. miles de facturas) en una sola carpeta física degradaría gravemente el rendimiento del sistema de archivos y entorpecería enormemente las consultas (XQuery/XPath). Por ello, proponemos una jerarquía organizada:
- `/xml_data/2026/03/facturas_emitidas/`
- `/xml_data/proveedores/catalogos_importacion/`

Esta organización facilita purgados programados (retenciones legales de 5 años), control perimetral de acceso, y optimiza radicalmente la localización de la información estructurada que las aplicaciones necesitan procesar diariamente.
