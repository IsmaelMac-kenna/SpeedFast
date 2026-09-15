package cl.speedfast.model;

import java.util.*;

public class ZonaDeCarga {
    private final Queue<Pedido> colaPedidos = new LinkedList<>();
    private final int capacidadMaxima;
    private boolean llegadaFinalizada = false;

    public ZonaDeCarga(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        System.out.println("[Zona de carga inicializada]\n");
    }

    public synchronized void agregarPedido(Pedido pedido) {
        while (colaPedidos.size() >= capacidadMaxima) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        colaPedidos.add(pedido);
        System.out.printf("Pedido %s agregado. Destino: %s%n", pedido.getIdPedido(), pedido.getDireccion());
        notifyAll();
    }

    public synchronized Pedido retirarPedido(String repartidorNombre) {
        while (colaPedidos.isEmpty()) {
            if (llegadaFinalizada) {
                return null; // Indica a los repartidores que no habrán más pedidos
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        Pedido pedido = colaPedidos.poll();
        notifyAll();
        return pedido;
    }

    public synchronized void finalizarLlegadas() {
        this.llegadaFinalizada = true;
        notifyAll();
    }

    public synchronized boolean estaVacia() {
        return colaPedidos.isEmpty();
    }
}