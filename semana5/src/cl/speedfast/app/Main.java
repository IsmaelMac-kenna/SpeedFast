package cl.speedfast.app;

import cl.speedfast.model.*;
import cl.speedfast.tareas.Repartidor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        //Iniciamos zona de carga
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga(5);

        //Agregamos los pedidos a la Zona de Carga
        Thread hiloLlegadaPedidos = new Thread(() -> {
            Pedido[] pedidosIniciales = {
                    new Pedido("#1", "Juanita Mamani", "Av. Providencia 185"),
                    new Pedido("#2", "Pedro Pascal", "Calle estado 201"),
                    new Pedido("#3", "Javiera Rojas", "Calle vergara 693"),
                    new Pedido("#4", "Roberto Carlos", "Moneda 1000"),
                    new Pedido("#5", "Elena Garay", "iagonal paraguay 491")
            };

            for (Pedido p : pedidosIniciales) {
                zonaDeCarga.agregarPedido(p);
            }
            System.out.println(); // Salto de línea estético
            zonaDeCarga.finalizarLlegadas();
        });

        //Iniciamos la producción de los pedidos
        hiloLlegadaPedidos.start();

        try {
            //Espera a que todos los pedidos ingresen a la zona
            hiloLlegadaPedidos.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Crear pool de hilos para los repartidores
        ExecutorService executorRepartidores = Executors.newFixedThreadPool(3);

        executorRepartidores.execute(new Repartidor("REP-01", "William", zonaDeCarga, 1500));
        executorRepartidores.execute(new Repartidor("REP-02", "Clare", zonaDeCarga, 1200));
        executorRepartidores.execute(new Repartidor("REP-03", "James", zonaDeCarga, 1800));

        // Cierre ordenado
        executorRepartidores.shutdown();
        try {
            if (executorRepartidores.awaitTermination(10, TimeUnit.SECONDS)) {
                if (zonaDeCarga.estaVacia()) {
                    System.out.println("[Zona de carga vacía]");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}