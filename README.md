 SpeedFast - Sistema de Gestión de Pedidos

## 📄 Descripción del Sistema
SpeedFast es una aplicación en Java desarrollada para gestionar y asignar repartidores a diferentes tipos de pedidos (Comida, Encomiendas y Compras Express). El sistema utiliza principios avanzados de Programación Orientada a Objetos, aplicando herencia, polimorfismo mediante sobreescritura (`@Override`) y sobrecarga de métodos para adaptar la lógica de asignación según los requerimientos específicos de cada servicio.

---

## 📦 Paquetes Utilizados y Clases Implementadas

### 1. Paquete `cl.speedfast.model`
Contiene la jerarquía de clases del modelo de dominio:

* **`Pedido` (Clase Base):** Define los atributos generales (`idPedido`, `cliente`, `direccionEntrega`) y los métodos base para la asignación de repartidores.
* **`PedidoComida` (Subclase):** Modela pedidos de restaurantes. Incluye atributos de `restaurante` y `tiempoPreparacion`, e implementa validaciones de mochila térmica.
* **`PedidoEncomienda` (Subclase):** Modela envíos de paquetes/documentos. Incluye atributos de `peso` y `volumen`, e implementa validaciones físicas del paquete.
* **`PedidoExpress` (Subclase):** Modela compras rápidas en tiendas o farmacias. Incluye atributos de `tienda` y `distancia`, e implementa asignación por disponibilidad inmediata.

### 2. Paquete `cl.speedfast`
Contiene el punto de entrada de la aplicación:

* **`Main` (Clase Principal):** Instancia los objetos de cada tipo de pedido, crea una colección polimórfica y ejecuta la demostración de sobreescritura y sobrecarga por consola.

---

## 🚀 Instrucciones para Ejecutar el Programa

1. **Abrir el proyecto:**
   * Abre **IntelliJ IDEA** (o el IDE de Java de tu preferencia).
   * Importa o abre la carpeta del proyecto.

2. **Ubicación de la Clase Principal:**
   * Navega en el explorador de archivos hasta el paquete `src/cl/speedfast/`.
   * Selecciona el archivo `Main.java`.

3. **Ejecución:**
   * Haz clic derecho sobre el archivo `Main.java` y selecciona **Run 'Main.main()'** (o presiona `Shift + F10`).
   * La salida del programa se desplegará en la consola integrada de IntelliJ IDEA.