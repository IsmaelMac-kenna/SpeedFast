package cl.speedfast.tareas;

import cl.speedfast.model.*;

import java.util.*;

public class Repartidor implements Runnable {
    private final String nombre;
    private final List<Pedido> pedidos;

    public Repartidor(String nombre, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.pedidos = pedidos;
    }

    @Override
    public void run() {
        for (Pedido p : pedidos) {
            String tipoPedido = p.getClass().getSimpleName();

            p.cambiarEstado(EstadoPedido.EN_RUTA);
            System.out.printf("[Repartidor: %s] Entregando %s #%d...%n", nombre, tipoPedido, p.getIdPedido());

            try {
                long milisegundos = (long) (p.calcularTiempoEntrega() * 1_000L);
                Thread.sleep(milisegundos);
            } catch (InterruptedException e) {
                System.err.printf("[ERROR] La entrega del repartidor %s fue interrumpida.%n", nombre);
                Thread.currentThread().interrupt();
                return;
            }

            p.cambiarEstado(EstadoPedido.ENTREGADO);
            System.out.printf("[Repartidor: %s] Pedido #%d entregado.%n", nombre, p.getIdPedido());
        }
    }
}