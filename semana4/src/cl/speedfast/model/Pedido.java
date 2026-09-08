package cl.speedfast.model;

import cl.speedfast.interfaces.Cancelable;
import cl.speedfast.interfaces.Despachable;
import cl.speedfast.interfaces.Rastreable;

public abstract class Pedido implements Despachable, Cancelable, Rastreable {
    protected int idPedido;
    protected String direccionEntrega;
    protected double distanciaKm;
    protected EstadoPedido estado;

    public Pedido(int idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getIdPedido() {
        return idPedido;
    }
    public String getDireccionEntrega() {
        return direccionEntrega;
    }
    public double getDistanciaKm() {
        return distanciaKm;
    }
    public EstadoPedido getEstado() {
        return estado;
    }

    public void cambiarEstado(EstadoPedido nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public abstract double calcularTiempoEntrega();

    public void mostrarResumen() {
        System.out.printf("Pedido #%d | Destino: %s | Distancia: %.1f km | Est. Tiempo: %.1f mins | Estado: %s%n",
                idPedido, direccionEntrega, distanciaKm, calcularTiempoEntrega(), estado);
    }

    @Override
    public void despachar() {
        cambiarEstado(EstadoPedido.EN_RUTA);
        System.out.printf("Despachando pedido #%d hacia %s%n", idPedido, direccionEntrega);
    }

    @Override
    public boolean cancelar() {
        cambiarEstado(EstadoPedido.CANCELADO);
        System.out.printf("Pedido #%d ha sido cancelado.%n", idPedido);
        return true;
    }

    @Override
    public String obtenerUbicacionActual() {
        return String.format("Pedido #%d en estado %s rumbo a %s",
                idPedido, estado, direccionEntrega);
    }
}