package cl.speedfast.tareas;


import cl.speedfast.model.*;

public class Repartidor implements Runnable {
    private final String idRepartidor;
    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;
    private final int tiempoEntregaMs;

    public Repartidor(String idRepartidor, String nombre, ZonaDeCarga zonaDeCarga, int tiempoEntregaMs) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.tiempoEntregaMs = tiempoEntregaMs;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Pedido pedido = zonaDeCarga.retirarPedido(nombre);

            if (pedido == null) {
                break; // Se terminaron los pedidos de la zona de carga
            }

            try {
                // 1. Retirar pedido
                System.out.printf("[Repartidor - %s] Retirando pedido %s...%n", nombre, pedido.getIdPedido());

                // 2. Cambiar a EN_REPARTO
                pedido.setEstado(EstadoPedido.EN_REPARTO);
                System.out.printf("[Repartidor - %s] Estado: %s%n", nombre, pedido.getEstado());

                // 3. Simular entrega
                System.out.printf("[Repartidor - %s] Entregando pedido %s...%n", nombre, pedido.getIdPedido());
                Thread.sleep(tiempoEntregaMs);

                // 4. Cambiar a ENTREGADO
                pedido.setEstado(EstadoPedido.ENTREGADO);
                System.out.printf("[Repartidor - %s] Estado: %s%n\n", nombre, pedido.getEstado());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}